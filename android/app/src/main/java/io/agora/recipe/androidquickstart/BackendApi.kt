package io.agora.recipe.androidquickstart
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody

@Serializable data class AgentConfig(
    val app_id: String, val token: String, val uid: String,
    val channel_name: String, val agent_uid: String
) {
    val appId get() = app_id; val channelName get() = channel_name; val agentUid get() = agent_uid
}
@Serializable private data class ConfigEnvelope(val code: Int, val data: AgentConfig)
@Serializable private data class StartData(val agent_id: String)
@Serializable private data class StartEnvelope(val code: Int, val data: StartData)

class BackendApi(private val baseUrl: String, private val client: OkHttpClient = OkHttpClient()) {
    private val json = Json { ignoreUnknownKeys = true }
    private val JSON = "application/json".toMediaType()

    fun getConfig(): AgentConfig {
        val req = Request.Builder().url("$baseUrl/get_config?uid=0").build()
        client.newCall(req).execute().use { r ->
            return json.decodeFromString<ConfigEnvelope>(r.body!!.string()).data
        }
    }
    fun startAgent(channelName: String, rtcUid: Int, userUid: Int): String {
        val body = """{"channelName":"$channelName","rtcUid":$rtcUid,"userUid":$userUid}""".toRequestBody(JSON)
        client.newCall(Request.Builder().url("$baseUrl/startAgent").post(body).build()).execute().use { r ->
            return json.decodeFromString<StartEnvelope>(r.body!!.string()).data.agent_id
        }
    }
    fun stopAgent(agentId: String) {
        val body = """{"agentId":"$agentId"}""".toRequestBody(JSON)
        client.newCall(Request.Builder().url("$baseUrl/stopAgent").post(body).build()).execute().close()
    }
}
