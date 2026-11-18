@file:OptIn(ExperimentalUuidApi::class)

package io.github.Kodxi.carspotterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import io.github.Kodxi.carspotterapp.ui.theme.CarSpotterAppTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import timber.log.Timber
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Timber.plant(Timber.DebugTree())
        setContent {
            val navController = rememberNavController()

            CarSpotterAppTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val cars = remember { AppData.carStore.findAll().toMutableStateList() }
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            CarSpotterMain(
                                cars = cars,
                                onAddCar = {
                                    Timber.d("Navigating To Add")
                                    navController.navigate("add")
                                },
                                onEditCar = { car ->
                                    navController.navigate("update/${car.id}")
                                    Timber.d("Navigating To Edit")

                                },
                                onDeleteCar = { car ->
                                    if (AppData.carStore.delete(car.id)) {
                                        cars.remove(car)
                                    }

                                },
                                onFindCar = {
                                    Timber.d("Navigating To Search")
                                    navController.navigate("search")
                                }
                            )
                        }
                        composable("add") {
                            CarSpotterAdd(
                                onSave = { car ->
                                    AppData.carStore.create(car)
                                    cars.add(car)
                                    Timber.d("Adding New Car; id=${car.id}, title=${car.brandModel}; \nNavigating To Main")
                                    navController.navigate("main")
                                },
                                onReturn = {
                                    Timber.d("No New Car Added; \nNavigating To Main")
                                    navController.navigate("main")
                                }
                            )
                        }
                        composable(
                            "update/{carId}",
                            arguments = listOf(navArgument("carId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val id = Uuid.parse(backStackEntry.arguments?.getString("carId")!!)
                            val car = AppData.carStore.findOne(id)
                            CarSpotterUpdate(
                                car = car,
                                onReturn = {
                                    Timber.d("Navigating To Main")
                                    navController.navigate("main")
                                },
                            )
                        }
                        composable("search") {
                            CarSpotterSearch(
                                onReturn = {
                                    Timber.d("Navigating To Main")
                                    navController.navigate("main")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}