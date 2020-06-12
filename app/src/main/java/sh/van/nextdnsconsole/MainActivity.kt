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
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.clickable
import androidx.ui.layout.Column
import androidx.ui.layout.padding
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.res.colorResource
import androidx.ui.res.stringResource
import androidx.ui.res.vectorResource
import androidx.ui.unit.dp
import sh.van.nextdns.model.*
import sh.van.nextdnsconsole.ui.*
import sh.van.nextdnsconsole.ui.login.LoginViewModel
import sh.van.nextdnsconsole.ui.login.LoginViewModel.AuthenticationState.Authenticated
import timber.log.Timber

@Composable
fun NextDnsConsoleScreen(
    setupLiveData: LiveData<Setup>,
    securityLiveData: LiveData<Security>,
    privacyLiveData: LiveData<Privacy>,
    parentalControlLiveData: LiveData<ParentalControl>,
    blacklistLiveData: LiveData<List<AccessControlListItem>>,
    whitelistLiveData: LiveData<List<AccessControlListItem>>,
    // analyticsLiveData: LiveData<Analytics>, // TODO
    logsLiveData: LiveData<Logs>,
    settingsLiveData: LiveData<Settings>,
    onNewConfigSelected: () -> Unit
) {
    val setup by setupLiveData.observeAsState()
    val security by securityLiveData.observeAsState()
    val privacy by privacyLiveData.observeAsState()
    val parentalControl by parentalControlLiveData.observeAsState()
    val blacklist by blacklistLiveData.observeAsState()
    val whitelist by whitelistLiveData.observeAsState()
//    val analytics by analyticsLiveData.observeAsState() // TODO
    val logs by logsLiveData.observeAsState()
    val settings by settingsLiveData.observeAsState()
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
    val configs = listOf("13d18c", "foobar")
    var selectedConfigIndex by state { 0 }
    var expanded by state { false }
    var selectedTabIndex by state { 0 }
    Scaffold(topAppBar = {
        Column {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    DropdownMenu(
                        toggle = { Text(configs[selectedConfigIndex]) },
                        toggleModifier = Modifier.clickable { expanded = !expanded },
                        expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        configs.forEachIndexed { index, s ->
                            DropdownMenuItem(onClick = {
                                // https://issuetracker.google.com/issues/158718697
                                TODO("Move the below code here in next Compose version")
                            }) {
                                Text(s, modifier = Modifier.clickable {
                                    expanded = false
                                    selectedConfigIndex = index
                                    onNewConfigSelected()
                                })
                            }
                        }
                    }
                }
            )
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
        }
    }) {
        VerticalScroller {
            when (items[selectedTabIndex].first) {
                R.string.menu_setup -> SetupScreen(setup)
                R.string.menu_security -> SecurityScreen(security)
                R.string.menu_privacy -> PrivacyScreen(privacy)
                R.string.menu_parental_control -> ParentalControlScreen(parentalControl)
                R.string.menu_blacklist -> AccessControlListScreen(blacklist)
                R.string.menu_whitelist -> AccessControlListScreen(whitelist)
//            R.string.menu_analytics -> AnalyticsScreen()
                R.string.menu_logs -> LogsScreen(logs)
                R.string.menu_settings -> SettingsScreen(settings)
            }
        }
    }

}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val loginViewModel: LoginViewModel by viewModels()
        val setupViewModel: SetupViewModel by viewModels()
        val securityViewModel: SecurityViewModel by viewModels()
        val privacyViewModel: PrivacyViewModel by viewModels()
        val parentalControlViewModel: ParentalControlViewModel by viewModels()
        val blacklistViewModel: AccessControlListViewModel by viewModels()
        val whitelistViewModel: AccessControlListViewModel by viewModels()
        val analyticsViewModel: AnalyticsViewModel by viewModels()
        val logsViewModel: LogsViewModel by viewModels()
        val settingsViewModel: SettingsViewModel by viewModels()
        loginViewModel.checkAuthState()
        loginViewModel.authenticationState.observe(this, Observer {
            when (it) {
                Authenticated -> {
                    val service = App.instance.service
                    setupViewModel.getSetup(service)
                    securityViewModel.getSecurity(service)
                    privacyViewModel.getPrivacy(service)
                    parentalControlViewModel.getParentalControl(service)
                    blacklistViewModel.getBlacklist(service)
                    whitelistViewModel.getWhitelist(service)
//                    analyticsViewModel.getAccessControlList(service)
                    logsViewModel.getLogs(service)
                    settingsViewModel.getSettings(service)
                }
                else -> Timber.d("Auth State: $it")
            }
        })

        setContent {
            MaterialTheme(
                colors = lightColorPalette(
                    primary = colorResource(id = R.color.colorPrimary),
                    primaryVariant = colorResource(id = R.color.colorAccent),
                    secondary = colorResource(id = R.color.colorAccent)
                )
            ) {
                NextDnsConsoleScreen(
                    setupViewModel.setupLiveData,
                    securityViewModel.securityLiveData,
                    privacyViewModel.privacyLiveData,
                    parentalControlViewModel.parentalControlLiveData,
                    blacklistViewModel.accessControlListLiveData,
                    whitelistViewModel.accessControlListLiveData,
//                analyticsViewModel.,
                    logsViewModel.logsLiveData,
                    settingsViewModel.settingsLiveData
                ) {}
            }
        }
    }
}
