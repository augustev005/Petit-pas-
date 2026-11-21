package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.PetitPasDatabase
import com.example.data.PetitPasRepository
import com.example.ui.components.PetitPasTopBar
import com.example.ui.screens.AudioConfirmationEweScreen
import com.example.ui.screens.AwakeAndNutritionScreen
import com.example.ui.screens.CaregiversAndCmsScreen
import com.example.ui.screens.Emergency118DangerAlertScreen
import com.example.ui.screens.FicheCmsDetailScreen
import com.example.ui.screens.HealthCarnetScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MySpaceScreen
import com.example.ui.theme.PetitPasTheme
import com.example.viewmodel.CmsDetailMode
import com.example.viewmodel.MainTab
import com.example.viewmodel.PetitPasUiState
import com.example.viewmodel.PetitPasViewModel
import com.example.viewmodel.SubScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val repository = remember {
                val db = PetitPasDatabase.getDatabase(context.applicationContext)
                PetitPasRepository(db.dao())
            }
            val viewModel: PetitPasViewModel = viewModel(
                factory = PetitPasViewModel.Factory(repository)
            )

            LaunchedEffect(Unit) {
                viewModel.initTtsIfNeeded(context.applicationContext)
            }

            val state by viewModel.uiState.collectAsStateWithLifecycle()

            PetitPasTheme(darkTheme = state.profile.isDarkMode) {
                PetitPasAppShell(
                    state = state,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun PetitPasAppShell(
    state: PetitPasUiState,
    viewModel: PetitPasViewModel
) {
    val context = LocalContext.current

    // Handle back press when on non-HOME main tabs if no subScreen is active
    if (state.subScreen == SubScreen.NONE && state.currentTab != MainTab.HOME) {
        BackHandler {
            viewModel.selectTab(MainTab.HOME)
        }
    }

    val topBarConfig = remember(state.subScreen, state.currentTab) {
        when (state.subScreen) {
            SubScreen.EMERGENCY_118 -> Triple(
                "Alerte Signes De Danger",
                "SAMU 118 • Liaison GSM directe",
                true
            )
            SubScreen.AUDIO_CONFIRMATION -> Triple(
                "Confirmation Audio",
                "Note vocale en Éwé (Eʋegbe)",
                true
            )
            SubScreen.FICHE_CMS_DETAIL -> Triple(
                "Fiche Cms Detail",
                "CMS Adidogomé • Hors ligne OK",
                true
            )
            SubScreen.NONE -> when (state.currentTab) {
                MainTab.HOME -> Triple("Petit Pas", "Hors ligne OK", false)
                MainTab.HEALTH_CARNET -> Triple("Petit Pas", "Carnet de Santé • Hors ligne OK", true)
                MainTab.AWAKE_NUTRITION -> Triple("Petit Pas", "Éveil & Nutrition • 8 mois", true)
                MainTab.CAREGIVERS_CMS -> Triple("Petit Pas", "CMS & Pharmacies de garde", true)
                MainTab.MY_SPACE -> Triple("Petit Pas", "Mon Espace • Hors ligne OK", true)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            PetitPasTopBar(
                title = topBarConfig.first,
                subtitle = topBarConfig.second,
                showBackButton = topBarConfig.third,
                isDarkMode = state.profile.isDarkMode,
                showLanguageDrawer = state.showLanguageDrawer,
                currentLanguage = state.profile.preferredLanguage,
                onBackClick = {
                    viewModel.navigateBack()
                },
                onMenuOrLangClick = { viewModel.toggleLanguageDrawer() },
                onToggleDarkMode = { viewModel.toggleDarkMode() },
                onVoiceAssistClick = {
                    val textToRead = when (state.subScreen) {
                        SubScreen.EMERGENCY_118 -> "Urgence Pédiatrique 118 Togo. Respirez calmement. Vous pouvez appeler le 118 gratuitement ou envoyer la fiche d'Amel par SMS."
                        SubScreen.AUDIO_CONFIRMATION -> state.currentAudioMessage.frText
                        SubScreen.FICHE_CMS_DETAIL -> "Fiche médicale d'Amel K. prête pour le CMS Adidogomé. Utilisable sans internet par Bluetooth, QR code ou PDF."
                        SubScreen.NONE -> when (state.currentTab) {
                            MainTab.HOME -> "Bienvenue sur Petit Pas. Chaque petit pas compte. Votre carnet de santé maternelle et infantile fonctionne cent pour cent hors connexion."
                            MainTab.HEALTH_CARNET -> "Carnet de santé d'Amel K., 8 mois. Poids 7 virgule 85 kilos, courbe OMS normale. 4 vaccins sur 5 reçus."
                            MainTab.AWAKE_NUTRITION -> "Éveil et nutrition à 8 mois. Bouillie enrichie togolaise maïs, soja et moringa."
                            MainTab.CAREGIVERS_CMS -> "Soignants et centres de santé. CMS Adidogomé ouvert 24 heures sur 24 avec sage-femme Docteur Akouavi Mensah."
                            MainTab.MY_SPACE -> "Mon espace personnel. Profil de ${state.profile.name} à ${state.profile.zone}. Données protégées sur ce téléphone."
                        }
                    }
                    viewModel.speakText(context, textToRead, state.profile.preferredLanguage)
                },
                onProfileClick = {
                    viewModel.selectTab(MainTab.MY_SPACE)
                },
                onSelectLanguage = { lang ->
                    viewModel.setLanguage(lang, context)
                }
            )
        },
        bottomBar = {
            if (state.subScreen == SubScreen.NONE) {
                PetitPasBottomNavigationBar(
                    currentTab = state.currentTab,
                    onSelectTab = { viewModel.selectTab(it) }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (state.subScreen) {
                SubScreen.EMERGENCY_118 -> {
                    Emergency118DangerAlertScreen(
                        state = state,
                        onBack = { viewModel.navigateBack() },
                        onDialNumber = { phone -> viewModel.dialPhoneNumber(context, phone) },
                        onListenPrompt = { text, lang -> viewModel.speakText(context, text, lang) },
                        onSendEmergencySms = { viewModel.sendEmergencySmsFiche(context) }
                    )
                }
                SubScreen.AUDIO_CONFIRMATION -> {
                    AudioConfirmationEweScreen(
                        state = state,
                        onBack = { viewModel.navigateBack() },
                        onTogglePlayPause = { viewModel.toggleAudioPlayback(context) },
                        onReplay = { viewModel.replayAudio(context) },
                        onSetVoiceSpeed = { speed -> viewModel.setVoiceSpeed(speed) },
                        onSelectLanguage = { lang -> viewModel.setLanguage(lang, context) },
                        onOpenCaregiverAdvice = {
                            viewModel.openSubScreen(
                                SubScreen.FICHE_CMS_DETAIL,
                                CmsDetailMode.CAREGIVER_MEMO
                            )
                        },
                        onOpenCarnet = { viewModel.selectTab(MainTab.HEALTH_CARNET) },
                        onOpenPdfAndBluetoothShare = {
                            viewModel.openSubScreen(
                                SubScreen.FICHE_CMS_DETAIL,
                                CmsDetailMode.OFFLINE_SHARE
                            )
                        }
                    )
                }
                SubScreen.FICHE_CMS_DETAIL -> {
                    FicheCmsDetailScreen(
                        state = state,
                        onBack = { viewModel.navigateBack() },
                        onSelectMode = { mode -> viewModel.setCmsDetailMode(mode) },
                        onExportPdf = { viewModel.exportOfficialPdf(context, openAfterGenerate = false) },
                        onBluetoothShare = { viewModel.triggerBluetoothShare() },
                        onWifiDirectShare = { viewModel.shareTextFiche(context) },
                        onSaveSdCard = { viewModel.exportOfficialPdf(context, openAfterGenerate = false) },
                        onShareSheet = { viewModel.shareTextFiche(context) },
                        onTogglePlayAudio = { viewModel.toggleAudioPlayback(context) },
                        onSelectLanguage = { lang -> viewModel.setLanguage(lang, context) },
                        onSetVoiceSpeed = { speed -> viewModel.setVoiceSpeed(speed) },
                        onSaveMemoToCarnet = { viewModel.saveMemoToCarnet() },
                        onScheduleTempReminder = { viewModel.scheduleTemperatureReminder() },
                        onDialCms = { viewModel.dialPhoneNumber(context, "+22822500000") }
                    )
                }
                SubScreen.NONE -> {
                    when (state.currentTab) {
                        MainTab.HOME -> HomeScreen(
                            state = state,
                            onListenWelcome = {
                                viewModel.speakText(
                                    context,
                                    "Bienvenue sur Petit Pas. Chaque petit pas compte. Votre carnet bienveillant fonctionne même sans réseau internet.",
                                    state.profile.preferredLanguage
                                )
                            },
                            onSelectLanguage = { lang -> viewModel.setLanguage(lang, context) },
                            onToggleRegistrationCard = { viewModel.toggleRegistrationCardOnHome() },
                            onSaveRegistration = { name, role, phone, isAnonymous, zone ->
                                viewModel.saveRegistration(name, role, phone, isAnonymous, zone, context)
                            },
                            onOpenCarnetDirect = { viewModel.selectTab(MainTab.HEALTH_CARNET) },
                            onOpenEmergency118Screen = {
                                viewModel.openSubScreen(SubScreen.EMERGENCY_118)
                            },
                            onDialNumber = { phone -> viewModel.dialPhoneNumber(context, phone) },
                            onListenText = { text ->
                                viewModel.speakText(context, text, state.profile.preferredLanguage)
                            },
                            onToggleVoiceSymptom = { viewModel.toggleVoiceSymptomRecording(context) },
                            onConfirmVoiceSymptoms = { viewModel.confirmVoiceSymptomsAndOpenAdvice(context) },
                            onOpenCaregiversTab = { viewModel.selectTab(MainTab.CAREGIVERS_CMS) }
                        )
                        MainTab.HEALTH_CARNET -> HealthCarnetScreen(
                            state = state,
                            onSelectLanguage = { lang -> viewModel.setLanguage(lang, context) },
                            onTogglePlayAudio = { viewModel.toggleAudioPlayback(context) },
                            onUpdateBiometrics = { weightKg, heightCm, tempCelsius ->
                                viewModel.updateActiveChildBiometrics(weightKg, heightCm, tempCelsius)
                            },
                            onToggleVaccine = { vaccine -> viewModel.toggleVaccine(vaccine) },
                            onExportPdfAndOpenDetail = {
                                viewModel.exportOfficialPdf(context, openAfterGenerate = false)
                                viewModel.openSubScreen(
                                    SubScreen.FICHE_CMS_DETAIL,
                                    CmsDetailMode.OFFLINE_SHARE
                                )
                            },
                            onOpenOfficialPassportA4 = {
                                viewModel.openSubScreen(
                                    SubScreen.FICHE_CMS_DETAIL,
                                    CmsDetailMode.OFFICIAL_PASSPORT
                                )
                            },
                            onTriggerBluetoothShare = {
                                viewModel.triggerBluetoothShare()
                                viewModel.openSubScreen(
                                    SubScreen.FICHE_CMS_DETAIL,
                                    CmsDetailMode.OFFLINE_SHARE
                                )
                            }
                        )
                        MainTab.AWAKE_NUTRITION -> AwakeAndNutritionScreen(
                            state = state,
                            onToggleMilestone = { milestone -> viewModel.toggleMilestone(milestone) },
                            onListenText = { text ->
                                viewModel.speakText(context, text, state.profile.preferredLanguage)
                            }
                        )
                        MainTab.CAREGIVERS_CMS -> CaregiversAndCmsScreen(
                            state = state,
                            onOpenCaregiverMemoScreen = {
                                viewModel.openSubScreen(
                                    SubScreen.FICHE_CMS_DETAIL,
                                    CmsDetailMode.CAREGIVER_MEMO
                                )
                            },
                            onOpenOfflineQrShareScreen = {
                                viewModel.openSubScreen(
                                    SubScreen.FICHE_CMS_DETAIL,
                                    CmsDetailMode.OFFLINE_SHARE
                                )
                            },
                            onOpenOfficialPassportScreen = {
                                viewModel.openSubScreen(
                                    SubScreen.FICHE_CMS_DETAIL,
                                    CmsDetailMode.OFFICIAL_PASSPORT
                                )
                            },
                            onOpenAudioConfirmationScreen = {
                                viewModel.openSubScreen(SubScreen.AUDIO_CONFIRMATION)
                            },
                            onOpenEmergency118Screen = {
                                viewModel.openSubScreen(SubScreen.EMERGENCY_118)
                            },
                            onDialNumber = { phone -> viewModel.dialPhoneNumber(context, phone) },
                            onTriggerBluetoothShare = { viewModel.triggerBluetoothShare() }
                        )
                        MainTab.MY_SPACE -> MySpaceScreen(
                            state = state,
                            onPlayWelcomeGreeting = {
                                viewModel.playAudioPrompt(context)
                            },
                            onListenText = { text ->
                                viewModel.speakText(context, text, state.profile.preferredLanguage)
                            },
                            onUpdateRole = { role -> viewModel.updateRole(role) },
                            onSelectChild = { childId -> viewModel.selectActiveChild(childId) },
                            onAddChild = { name, ageMonths, gender, birthDate, weightKg, heightCm ->
                                viewModel.addNewChild(name, ageMonths, gender, birthDate, weightKg, heightCm)
                            },
                            onOpenCarnetTab = { viewModel.selectTab(MainTab.HEALTH_CARNET) },
                            onSelectLanguage = { lang -> viewModel.setLanguage(lang, context) },
                            onSetVoiceSpeed = { speed -> viewModel.setVoiceSpeed(speed) },
                            onToggleDarkMode = { viewModel.toggleDarkMode() },
                            onExportPdf = {
                                viewModel.exportOfficialPdf(context, openAfterGenerate = true)
                            },
                            onBluetoothTransfer = {
                                viewModel.triggerBluetoothShare()
                                viewModel.openSubScreen(
                                    SubScreen.FICHE_CMS_DETAIL,
                                    CmsDetailMode.OFFLINE_SHARE
                                )
                            },
                            onSaveSdCardBackup = {
                                viewModel.exportOfficialPdf(context, openAfterGenerate = false)
                            },
                            onEditProfile = { name, phone, zone ->
                                viewModel.updateProfileInfo(name, phone, zone)
                            }
                        )
                    }
                }
            }

            // Floating Toast / Notification Banner
            AnimatedVisibility(
                visible = state.toastMessage != null,
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                state.toastMessage?.let { msg ->
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.inverseSurface,
                        contentColor = MaterialTheme.colorScheme.inverseOnSurface,
                        shadowElevation = 8.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("toast_banner")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.inversePrimary,
                                modifier = Modifier.size(22.dp)
                            )
                            Text(
                                text = msg,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = {
                                    viewModel.speakText(context, msg, state.profile.preferredLanguage)
                                },
                                modifier = Modifier.size(36.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = "Écouter notification",
                                    tint = MaterialTheme.colorScheme.inversePrimary,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            IconButton(
                                onClick = { viewModel.dismissToast() },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Fermer",
                                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private data class NavTabItem(
    val tab: MainTab,
    val label: String,
    val icon: ImageVector,
    val testTag: String
)

@Composable
private fun PetitPasBottomNavigationBar(
    currentTab: MainTab,
    onSelectTab: (MainTab) -> Unit
) {
    val navItems = remember {
        listOf(
            NavTabItem(MainTab.HOME, "Accueil", Icons.Default.Home, "nav_tab_accueil"),
            NavTabItem(MainTab.HEALTH_CARNET, "Suivi santé", Icons.Default.MedicalServices, "nav_tab_suivi_sante"),
            NavTabItem(MainTab.AWAKE_NUTRITION, "Éveil", Icons.Default.WbSunny, "nav_tab_eveil"),
            NavTabItem(MainTab.CAREGIVERS_CMS, "Soignants", Icons.Default.LocalHospital, "nav_tab_soignants"),
            NavTabItem(MainTab.MY_SPACE, "Mon Espace", Icons.AutoMirrored.Filled.MenuBook, "nav_tab_mon_espace")
        )
    }

    NavigationBar(
        windowInsets = WindowInsets.navigationBars,
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        navItems.forEach { item ->
            val selected = currentTab == item.tab
            NavigationBarItem(
                selected = selected,
                onClick = { onSelectTab(item.tab) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.SemiBold
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.testTag(item.testTag)
            )
        }
    }
}
