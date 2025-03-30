package com.example.kalah47

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.kalah47.core.AboutSystemRoute
import com.example.kalah47.core.BotGameRoute
import com.example.kalah47.core.GameRuleRoute
import com.example.kalah47.core.LocalGameRoute
import com.example.kalah47.core.MY_TAG
import com.example.kalah47.core.OnlineGameRoute
import com.example.kalah47.core.OnlineRoute
import com.example.kalah47.core.RootRoute
import com.example.kalah47.core.SettingsRoute
import com.example.kalah47.feature.about_system_page.AboutSystemPage
import com.example.kalah47.feature.common_components.getPlayersInfo
import com.example.kalah47.feature.common_components.getSettingsData
import com.example.kalah47.feature.game_page.GamePage
import com.example.kalah47.feature.game_page.viewmodel.PitsVMBot
import com.example.kalah47.feature.game_page.viewmodel.PitsVMOnline
import com.example.kalah47.feature.game_page.viewmodel.PitsViewModel
import com.example.kalah47.feature.game_rule_page.GameRule
import com.example.kalah47.feature.online_page.OnlinePage
import com.example.kalah47.feature.root_page.RootPage
import com.example.kalah47.feature.settings.SettingsPage
import com.example.kalah47.model.PlayerModel
import com.example.kalah47.repository.DataStoreManager
import com.example.kalah47.repository.PlayersInfo
import com.example.kalah47.repository.SettingsData
import com.example.kalah47.ui.theme.AppTheme
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinApplication
import org.koin.dsl.koinApplication
import org.koin.dsl.module


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val dataStore = DataStoreManager(this)
        setContent {
            AppTheme {
                Log.d(MY_TAG, "Activity onCreate")

                HideSystemUI()
//=============================================================================================
//=============================================================================================
//          * Define navigator
                val navController = rememberNavController()
//=============================================================================================
//=============================================================================================
//          * Create singleton of navigator as Koin and register it in context
                val koinModule = module {
                    single<NavController>(createdAtStart = true) { navController }
                    single<DataStoreManager>(createdAtStart = true) { dataStore }
                }

                //          * Create a koinApplication that will be managed with MainActivity
                fun koinN() = koinApplication {
                    androidLogger()
                    modules(koinModule)
                }

                KoinApplication(application = ::koinN) {
//                  * For preload data
                    val q = getSettingsData()
                    val p = getPlayersInfo()
//=============================================================================================
//=============================================================================================
                    setNavHost(navController, q, p)
//=============================================================================================
//=============================================================================================
                }
            }
        }
    }


    @SuppressLint("ComposableNaming")
    @Composable
    private fun setNavHost(
        navController: NavHostController,
        q: MutableState<SettingsData>,
        p: MutableState<PlayersInfo>
    ) {

//      * Define navigators routes after koin because koin should register navigator before
//      * app's start (it will be with navHost register startDectination)
        NavHost(
            navController = navController,
            startDestination = RootRoute
        ) {
//          * Define app's page
            composable<RootRoute> {
                RootPage()
            }
            composable<LocalGameRoute> {
                GamePage(viewModel<PitsViewModel>())
            }
            composable<BotGameRoute> {
                val pitVM = viewModel<PitsVMBot>()

                LaunchedEffect(pitVM) {
                    val plrModel = PlayerModel(info = p.value)

                    pitVM.kalahModel.playersJoint[0] = plrModel
                }
                GamePage(pitVM)


            }
            composable<SettingsRoute> {
                SettingsPage()
            }
            composable<OnlineRoute> {
                OnlinePage()
            }
            composable<OnlineGameRoute> {
                val args = it.toRoute<OnlineGameRoute>()

                val pitVM: PitsVMOnline = viewModel()

                if (args.gameId == "-1" || args.gameId == pitVM.kalahModel.gameId.value) {
                    GamePage(pitVM)
                    return@composable
                }


                pitVM.kalahModel.gameId.value = args.gameId
                val plrModel = PlayerModel(info = p.value, id = args.startPlayerId)

                if (args.isCreateLobby!!) {
                    pitVM.setKalahSettings(
                        q.value.pitsCountOneSide,
                        q.value.jewelCountInOnePit
                    )
                    pitVM.createModelInFirestore(playerModel = plrModel)


                } else {
                    pitVM.restoreFromFirestore(plrModel)
                }
                GamePage(pitVM)
            }

            composable<GameRuleRoute> {
                GameRule()
            }

            composable<AboutSystemRoute> {
                AboutSystemPage()
            }
        }
    }

    @Composable
    private fun HideSystemUI() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }
}
