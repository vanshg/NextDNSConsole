package sh.van.nextdnsconsole

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.layout.Column
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.res.stringResource
import androidx.ui.res.vectorResource
import sh.van.nextdns.model.Setup
import sh.van.nextdnsconsole.ui.SetupScreen
import sh.van.nextdnsconsole.ui.SetupViewModel
import sh.van.nextdnsconsole.ui.login.LoginViewModel
import sh.van.nextdnsconsole.ui.login.LoginViewModel.AuthenticationState.Authenticated
import timber.log.Timber

@Composable
fun NextDnsConsoleScreen(setupLiveData: LiveData<Setup>, onNewConfigSelected: () -> Unit) {
    val setup by setupLiveData.observeAsState()
    val items = listOf(
        R.string.menu_setup to R.drawable.ic_menu_setup,
        R.string.menu_security to R.drawable.ic_menu_security,
        R.string.menu_privacy to R.drawable.ic_menu_privacy,
        R.string.menu_parental_control to R.drawable.ic_menu_parental_control,
        R.string.menu_blacklist to R.drawable.ic_menu_blacklist,
        R.string.menu_whitelist to R.drawable.ic_menu_whitelist,
        R.string.menu_analytics to R.drawable.ic_menu_analytics,
        R.string.menu_logs to R.drawable.ic_menu_logs,
        R.string.menu_settings to R.drawable.ic_menu_settings
    )
    Column {
        val configs = listOf("13d18c", "foobar")
        var selectedConfigIndex by state { 0 }
        var expanded by state { false }
        TopAppBar(
            title = { Text(stringResource(R.string.app_name)) },
            actions = {
                DropdownMenu(
                    toggle = { Text(configs[selectedConfigIndex]) },
                    toggleModifier = Modifier.clickable { expanded = !expanded },
                    expanded = expanded,
                    onDismissRequest = { expanded = false }) {
                    configs.forEachIndexed { index, s ->
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                selectedConfigIndex = index
                        }) {
                            Text(s)
                        }
                    }
                }
            }
        )
        var selectedTabIndex by state { 0 }
        TabRow(
            selectedIndex = selectedTabIndex,
            scrollable = true,
            items = items
        ) { index, item ->
            Tab(
                text = { Text(text = stringResource(id = item.first)) },
                icon = { Icon(asset = vectorResource(id = item.second)) },
                selected = selectedTabIndex == index,
                onSelected = { selectedTabIndex = index }
            )
        }
        when (items[selectedTabIndex].first) {
            R.string.menu_setup -> SetupScreen(setup)
            R.string.menu_security -> Text("This is security") // SecurityScreen()
            R.string.menu_privacy -> Text("This is privacy") // PrivacyScreen()
            R.string.menu_parental_control -> Text("This is parental_control") // ParentalControlScreen()
            R.string.menu_blacklist -> Text("This is blacklist") // BlacklistScreen()
            R.string.menu_whitelist -> Text("This is whitelist") // WhitelistScreen()
            R.string.menu_analytics -> Text("This is analytics") // AnalyticsScreen()
            R.string.menu_logs -> Text("This is logs") // LogsScreen()
            R.string.menu_settings -> Text("This is settings") // SettingsScreen()
        }
    }
}

class MainActivity : AppCompatActivity() {

//    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginViewModel: LoginViewModel by viewModels()
        val setupViewModel: SetupViewModel by viewModels()
        loginViewModel.checkAuthState()
        loginViewModel.authenticationState.observe(this, Observer {
            when (it) {
                Authenticated -> setupViewModel.getSetup(App.instance.service)
                else -> Timber.d("Auth State: $it")
            }
        })
        setContent { NextDnsConsoleScreen(setupViewModel.setupLiveData) {} }
//        val binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        setSupportActionBar(binding.appBar.toolbar)
//        appBarConfiguration = AppBarConfiguration(binding.navView.menu, binding.drawerLayout)
//        val navController = findNavController(R.id.nav_host_fragment)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        binding.navView.setupWithNavController(navController)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }
}
