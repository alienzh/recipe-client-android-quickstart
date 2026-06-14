package io.agora.recipe.androidquickstart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import io.agora.recipe.androidquickstart.ui.CallScreen
import io.agora.recipe.androidquickstart.ui.LandingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val autoConnect = intent?.getBooleanExtra("AUTO_CONNECT", false) ?: false
        setContent {
            MaterialTheme {
                Surface {
                    val vm: CallViewModel = viewModel()
                    val phase by vm.phase.collectAsState()
                    val turns by vm.turns.collectAsState()
                    val micMuted by vm.micMuted.collectAsState()
                    val agentState by vm.agentState.collectAsState()
                    val error by vm.errorMessage.collectAsState()

                    LaunchedEffect(autoConnect) {
                        if (autoConnect) vm.connect()
                    }

                    when (phase) {
                        CallPhase.Idle, CallPhase.Error -> LandingScreen(
                            errorMessage = error,
                            onConnect = { vm.connect() },
                        )
                        CallPhase.Connecting, CallPhase.InCall -> CallScreen(
                            connecting = phase == CallPhase.Connecting,
                            agentState = agentState,
                            micMuted = micMuted,
                            turns = turns,
                            onToggleMic = { vm.toggleMic() },
                            onEnd = { vm.end() },
                        )
                    }
                }
            }
        }
    }
}
