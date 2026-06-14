package io.agora.recipe.androidquickstart
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.assertEquals
import org.junit.Test

class BackendApiTest {
    @Test fun getConfigDecodesEnvelope() {
        val server = MockWebServer()
        server.enqueue(MockResponse().setBody("""{"code":0,"data":{"app_id":"a","token":"t","uid":"123","channel_name":"c","agent_uid":"999"},"msg":"ok"}"""))
        server.start()
        val api = BackendApi(server.url("/").toString().trimEnd('/'))
        val cfg = api.getConfig()
        assertEquals("a", cfg.appId); assertEquals("123", cfg.uid); assertEquals("999", cfg.agentUid)
        server.shutdown()
    }
    @Test fun startAgentReturnsId() {
        val server = MockWebServer()
        server.enqueue(MockResponse().setBody("""{"code":0,"data":{"agent_id":"ag-1"},"msg":"ok"}"""))
        server.start()
        val api = BackendApi(server.url("/").toString().trimEnd('/'))
        assertEquals("ag-1", api.startAgent("c", 999, 123))
        server.shutdown()
    }
}
