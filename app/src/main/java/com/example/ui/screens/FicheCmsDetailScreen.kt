package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Emergency
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.SdCard
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.ui.components.OfflineStatusPill
import com.example.ui.components.OmsGrowthChartCanvas
import com.example.ui.components.QrCodeMatrixBox
import com.example.ui.components.SoundWaveVisualizer
import com.example.ui.theme.OnPrimaryFixed
import com.example.ui.theme.OnSecondaryFixed
import com.example.ui.theme.OnTertiaryFixed
import com.example.ui.theme.PrimaryFixed
import com.example.ui.theme.SecondaryFixed
import com.example.ui.theme.TertiaryFixed
import com.example.viewmodel.CmsDetailMode
import com.example.viewmodel.PetitPasUiState

@Composable
fun FicheCmsDetailScreen(
    state: PetitPasUiState,
    onBack: () -> Unit,
    onSelectMode: (CmsDetailMode) -> Unit,
    onExportPdf: () -> Unit,
    onBluetoothShare: () -> Unit,
    onWifiDirectShare: () -> Unit,
    onSaveSdCard: () -> Unit,
    onShareSheet: () -> Unit,
    onTogglePlayAudio: () -> Unit,
    onSelectLanguage: (String) -> Unit,
    onSetVoiceSpeed: (Float) -> Unit,
    onSaveMemoToCarnet: () -> Unit,
    onScheduleTempReminder: () -> Unit,
    onDialCms: () -> Unit
) {
    BackHandler(onBack = onBack)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .testTag("fiche_cms_detail_list"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Mode Switcher Bar: 1) Partage & QR  2) Mémo Sage-Femme  3) Passeport Officiel A4
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val tabs = listOf(
                    CmsDetailMode.OFFLINE_SHARE to "Partage & QR",
                    CmsDetailMode.CAREGIVER_MEMO to "Mémo Dr. Akouavi",
                    CmsDetailMode.OFFICIAL_PASSPORT to "Passeport A4"
                )
                tabs.forEach { (mode, label) ->
                    val selected = state.cmsDetailMode == mode
                    Surface(
                        color = if (selected) MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = RoundedCornerShape(9.dp),
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(9.dp))
                            .clickable { onSelectMode(mode) }
                            .testTag("cms_mode_tab_${mode.name.lowercase()}")
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(vertical = 9.dp, horizontal = 4.dp)
                        )
                    }
                }
            }
        }

        when (state.cmsDetailMode) {
            CmsDetailMode.OFFLINE_SHARE -> {
                item {
                    OfflineShareFicheContent(
                        state = state,
                        onExportPdf = onExportPdf,
                        onBluetoothShare = onBluetoothShare,
                        onWifiDirectShare = onWifiDirectShare,
                        onSaveSdCard = onSaveSdCard,
                        onTogglePlayAudio = onTogglePlayAudio,
                        onSelectLanguage = onSelectLanguage,
                        onDialCms = onDialCms,
                        onNextMode = { onSelectMode(CmsDetailMode.OFFICIAL_PASSPORT) },
                        onBack = onBack
                    )
                }
            }

            CmsDetailMode.CAREGIVER_MEMO -> {
                item {
                    CaregiverMemoContent(
                        state = state,
                        onTogglePlayAudio = onTogglePlayAudio,
                        onSelectLanguage = onSelectLanguage,
                        onSetVoiceSpeed = onSetVoiceSpeed,
                        onSaveMemoToCarnet = onSaveMemoToCarnet,
                        onScheduleTempReminder = onScheduleTempReminder
                    )
                }
            }

            CmsDetailMode.OFFICIAL_PASSPORT -> {
                item {
                    OfficialTogolesePassportContent(
                        state = state,
                        onExportPdf = onExportPdf,
                        onBluetoothShare = onBluetoothShare,
                        onShareSheet = onShareSheet,
                        onTogglePlayAudio = onTogglePlayAudio,
                        onBack = onBack
                    )
                }
            }
        }
    }
}

@Composable
private fun OfflineShareFicheContent(
    state: PetitPasUiState,
    onExportPdf: () -> Unit,
    onBluetoothShare: () -> Unit,
    onWifiDirectShare: () -> Unit,
    onSaveSdCard: () -> Unit,
    onTogglePlayAudio: () -> Unit,
    onSelectLanguage: (String) -> Unit,
    onDialCms: () -> Unit,
    onNextMode: () -> Unit,
    onBack: () -> Unit
) {
    val child = state.activeChild
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OfflineStatusPill(
            text = "● En ligne & Hors connexion : OK",
            trailingBadge = "Valide CMS"
        )

        // Download Confirmation Banner
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.14f),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(20.dp))
                    }
                    Column {
                        Text(
                            text = "✓ La fiche de consultation CMS a bien été téléchargée",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Disponible hors-ligne dans vos documents",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceContainerLowest)
                        .clickable(onClick = onTogglePlayAudio),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Écouter confirmation", tint = MaterialTheme.colorScheme.secondary)
                }
            }
        }

        // Document Card Summary + QR Code
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    // Thumbnail PDF Preview
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .width(88.dp)
                            .height(116.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height(5.dp)
                                        .clip(RoundedCornerShape(99.dp))
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                                )
                                Icon(Icons.Default.LocalHospital, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                            }
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Box(modifier = Modifier.fillMaxWidth().height(5.dp).clip(RoundedCornerShape(4.dp)).background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)))
                                Box(modifier = Modifier.fillMaxWidth(0.8f).height(5.dp).clip(RoundedCornerShape(4.dp)).background(MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f)))
                                Box(modifier = Modifier.fillMaxWidth(0.6f).height(5.dp).clip(RoundedCornerShape(4.dp)).background(MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)))
                                Box(modifier = Modifier.fillMaxWidth().height(7.dp).clip(RoundedCornerShape(4.dp)).background(SecondaryFixed))
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("PDF", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                Icon(Icons.Default.Verified, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(13.dp))
                            }
                        }
                    }

                    // File Meta
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "PRÊT POUR CONSULTATION",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Fiche_Medicale_Amel_K_CMS_Adidogome.pdf",
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Taille : 245 Ko • Généré aujourd'hui hors-ligne",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Surface(
                                color = MaterialTheme.colorScheme.surfaceContainer,
                                shape = RoundedCornerShape(999.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(Icons.Default.WifiOff, contentDescription = null, modifier = Modifier.size(13.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                    Text("100% Autonome", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                            Surface(
                                color = PrimaryFixed.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(999.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(Icons.Default.Shield, contentDescription = null, modifier = Modifier.size(13.dp), tint = MaterialTheme.colorScheme.primary)
                                    Text("Signé AES", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                    }
                }

                // Quick Scan Area for Nurse / Agent
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Contrôle soignant instantané",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Le soignant du CMS Adidogomé peut scanner ce code sécurisé directement sur votre écran.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        QrCodeMatrixBox(modifier = Modifier.size(68.dp))
                    }
                }
            }
        }

        // Primary Emerald PDF Download Button
        Button(
            onClick = onExportPdf,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .testTag("btn_download_cms_pdf")
        ) {
            Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Télécharger / Exporter en PDF la fiche CMS",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Enregistrer une copie locale (245 Ko)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                )
            }
        }

        // Local Offline Sharing Options (Transmettre sans Internet)
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Transmettre sans Internet",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Proximité locale",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            // Bluetooth CMS
            OfflineShareOptionCard(
                icon = Icons.Default.Bluetooth,
                iconBg = PrimaryFixed,
                iconTint = OnPrimaryFixed,
                title = "Transmettre par Bluetooth au soignant CMS",
                subtitle = if (state.isBluetoothSyncing) state.bluetoothStatus else "Envoi sécurisé vers la tablette du centre de santé",
                onClick = onBluetoothShare
            )

            // Wi-Fi Direct
            OfflineShareOptionCard(
                icon = Icons.Default.NearMe,
                iconBg = SecondaryFixed,
                iconTint = OnSecondaryFixed,
                title = "Partager par Wi-Fi Direct / Local",
                subtitle = "Connexion directe téléphone à téléphone",
                onClick = onWifiDirectShare
            )

            // Save to SD Card
            OfflineShareOptionCard(
                icon = Icons.Default.SdCard,
                iconBg = MaterialTheme.colorScheme.surfaceVariant,
                iconTint = MaterialTheme.colorScheme.onSurfaceVariant,
                title = "Enregistrer sur la Carte SD",
                subtitle = "Dossier /Sante_Togo/${child?.name?.replace(" ", "_") ?: "Amel"}_Carnet.pdf",
                onClick = onSaveSdCard
            )
        }

        // Audio Confirmation Section (National Languages)
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(SecondaryFixed),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.RecordVoiceOver, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(18.dp))
                        }
                        Text(
                            text = "Confirmation vocale parlée",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Surface(
                        color = SecondaryFixed.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(999.dp)
                    ) {
                        Text(
                            text = "Lecture auto prête",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // Active Audio Player
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .clickable(onClick = onTogglePlayAudio),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (state.isPlayingAudio) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = "Lecture / Pause",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = when (state.profile.preferredLanguage) {
                                        "kabiye" -> "Kabɩyɛ • Voix locale"
                                        "fr" -> "Français • Audio officiel"
                                        else -> "Éwé (Eʋegbe) • Voix communautaire"
                                    },
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "0:19 / 0:38",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            LinearProgressIndicator(
                                progress = { 0.5f },
                                color = MaterialTheme.colorScheme.primaryContainer,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(99.dp))
                            )
                        }
                    }
                }

                // Synchronized Speech Box (Éwé & French)
                Surface(
                    color = PrimaryFixed.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "TEXTE LU EN ÉWÉ",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "« Wò agbalẽvi \"Fiche Médicale Amel\" le wò kaƒomɔ me xoxoxo. Éle dedie le wò agbalẽkpa me hena dɔnɔkplɔla le CMS Adidogomé. »",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "TRADUCTION RASSURANTE EN FRANÇAIS",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "« Votre carnet médical pour Amel est prêt et sécurisé dans votre téléphone. Vous pouvez le montrer directement à la sage-femme au CMS Adidogomé. »",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { onSelectLanguage("kabiye") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f).height(44.dp)
                    ) {
                        Text("Écouter en Kabɩyɛ", style = MaterialTheme.typography.labelSmall)
                    }
                    Button(
                        onClick = { onSelectLanguage("fr") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f).height(44.dp)
                    ) {
                        Text("Écouter en Français", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }

        // Bottom Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onDialCms,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryFixed,
                    contentColor = OnSecondaryFixed
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).height(48.dp)
            ) {
                Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("Contacter CMS", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            }
            Button(
                onClick = onNextMode,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f).height(48.dp)
            ) {
                Text("Passeport A4", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(6.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }

        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("Retourner au carnet de santé", style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun OfflineShareOptionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(iconBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(24.dp))
                }
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
private fun CaregiverMemoContent(
    state: PetitPasUiState,
    onTogglePlayAudio: () -> Unit,
    onSelectLanguage: (String) -> Unit,
    onSetVoiceSpeed: (Float) -> Unit,
    onSaveMemoToCarnet: () -> Unit,
    onScheduleTempReminder: () -> Unit
) {
    val memo = state.memos.firstOrNull()
    val child = state.activeChild

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        OfflineStatusPill(
            text = "● Enregistré en mémoire locale",
            trailingBadge = "Synchro hors-ligne active"
        )

        // Confirmation Card: Call Completed with Dr. Akouavi Mensah
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MedicalServices,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Column {
                        Surface(
                            color = SecondaryFixed,
                            shape = RoundedCornerShape(999.dp)
                        ) {
                            Text(
                                text = "Échange validé",
                                style = MaterialTheme.typography.labelSmall,
                                color = OnSecondaryFixed,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = memo?.doctorName ?: "Dr. Akouavi Mensah",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = memo?.doctorRole ?: "Sage-femme clinicienne • CMS Adidogomé",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.CallEnd, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                            Text(memo?.timestampLabel ?: "Aujourd'hui à 10:42", style = MaterialTheme.typography.labelMedium)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Icon(Icons.Default.Timer, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(18.dp))
                            Text(memo?.durationLabel ?: "4 min 12s", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Audio Voice Note Player Card
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.Mic, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(22.dp))
                        Text("Mémo vocal de la sage-femme", style = MaterialTheme.typography.headlineSmall)
                    }
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(999.dp)
                    ) {
                        Text("Clarté HD", style = MaterialTheme.typography.labelSmall, modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .clickable(onClick = onTogglePlayAudio),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (state.isPlayingAudio) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = "Jouer mémo",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            Box(modifier = Modifier.weight(1f)) {
                                SoundWaveVisualizer(isPlaying = state.isPlayingAudio)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("0:${state.audioElapsedSeconds.toString().padStart(2, '0')}", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                                Text("1:45", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Vitesse d'écoute", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(999.dp))
                                    .background(MaterialTheme.colorScheme.surfaceContainer)
                                    .padding(2.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                listOf(0.8f to "0.8x", 1.0f to "1.0x", 1.2f to "1.2x").forEach { (spd, lbl) ->
                                    val active = kotlin.math.abs(state.profile.voiceSpeed - spd) < 0.08f
                                    Surface(
                                        color = if (active) MaterialTheme.colorScheme.surfaceContainerLowest else Color.Transparent,
                                        shape = RoundedCornerShape(999.dp),
                                        modifier = Modifier.clickable { onSetVoiceSpeed(spd) }
                                    ) {
                                        Text(
                                            text = lbl,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Text(
                    text = "TRADUCTIONS VOCALES AUTOMATIQUES",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )

                // Ewe Audio Capsule
                Surface(
                    color = SecondaryFixed.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onSelectLanguage("ewe") }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(
                                modifier = Modifier.size(36.dp).clip(CircleShape).background(MaterialTheme.colorScheme.secondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondary, modifier = Modifier.size(18.dp))
                            }
                            Column {
                                Text("Écouter en Eʋegbe (Éwé)", style = MaterialTheme.typography.labelLarge, color = OnSecondaryFixed)
                                Text("Nufiame le Eʋegbe me • 1 min 50s", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                            }
                        }
                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                    }
                }

                // French Audio Capsule
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onSelectLanguage("fr") }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Box(
                                modifier = Modifier.size(36.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Headphones, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(18.dp))
                            }
                            Column {
                                Text("Écouter en Français", style = MaterialTheme.typography.labelLarge)
                                Text("Voix claire et simplifiée", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        Icon(Icons.Default.PlayArrow, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }

        // 3 Conseils clés à appliquer
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("3 Conseils clés à appliquer", style = MaterialTheme.typography.headlineSmall)
            Text("Important", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }

        // Advice 1: SRO
        ClinicalAdviceCard(
            badgeCode = "SRO",
            badgeBg = PrimaryFixed,
            badgeFg = OnPrimaryFixed,
            icon = Icons.Default.WaterDrop,
            title = "1. Hydratation immédiate",
            description = "Donner une solution de réhydratation orale (SRO) par petites gorgées régulières après chaque selle liquide ou vomissement."
        )

        // Advice 2: 2H
        ClinicalAdviceCard(
            badgeCode = "2H",
            badgeBg = SecondaryFixed,
            badgeFg = OnSecondaryFixed,
            icon = Icons.Default.DeviceThermostat,
            title = "2. Surveillance température",
            description = "Prendre la température sous l'aisselle toutes les 2 heures. Découvrir ${child?.name ?: "Amel"} avec un pagne léger et aérer la chambre."
        )

        // Advice 3: 24H
        ClinicalAdviceCard(
            badgeCode = "24H",
            badgeBg = MaterialTheme.colorScheme.tertiaryContainer,
            badgeFg = MaterialTheme.colorScheme.onTertiary,
            icon = Icons.Default.Emergency,
            title = "3. Alerte : CMS immédiat",
            description = "Si la température dépasse 38,5°C ou en cas de vomissements répétés, venez sans délai au CMS Adidogomé (ouvert 24h/24)."
        )

        // Call to Actions
        Button(
            onClick = onSaveMemoToCarnet,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .testTag("btn_save_memo_to_carnet")
        ) {
            Icon(Icons.Default.BookmarkAdded, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Enregistrer dans le carnet d'${child?.name ?: "Amel"}", style = MaterialTheme.typography.labelLarge)
        }

        Button(
            onClick = onScheduleTempReminder,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("btn_schedule_2h_reminder")
        ) {
            Icon(Icons.Default.AlarmOn, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = if (memo?.reminderScheduledTime != null) "✓ Rappel actif : ${memo.reminderScheduledTime}" else "Programmer un rappel dans 2h pour la température",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
private fun ClinicalAdviceCard(
    badgeCode: String,
    badgeBg: Color,
    badgeFg: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Surface(
                color = badgeBg,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.size(56.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(icon, contentDescription = null, tint = badgeFg, modifier = Modifier.size(26.dp))
                    Text(badgeCode, style = MaterialTheme.typography.labelSmall, color = badgeFg, fontWeight = FontWeight.Bold)
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(title, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
                Text(description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun OfficialTogolesePassportContent(
    state: PetitPasUiState,
    onExportPdf: () -> Unit,
    onBluetoothShare: () -> Unit,
    onShareSheet: () -> Unit,
    onTogglePlayAudio: () -> Unit,
    onBack: () -> Unit
) {
    val child = state.activeChild
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        // 1. Document Action Deck
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = PrimaryFixed.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(999.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.Verified, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                            Text(
                                text = "DOCUMENT PDF CERTIFIÉ • SIGNÉ",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Text("● Prêt pour tirage", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                }

                Button(
                    onClick = onExportPdf,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Icon(Icons.Default.Print, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Imprimer / Télécharger", style = MaterialTheme.typography.labelLarge)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onBluetoothShare,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f).height(48.dp)
                    ) {
                        Icon(Icons.Default.Bluetooth, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Relayer au CMS", style = MaterialTheme.typography.labelMedium)
                    }

                    Button(
                        onClick = onShareSheet,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SecondaryFixed,
                            contentColor = OnSecondaryFixed
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f).height(48.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Partager la fiche", style = MaterialTheme.typography.labelMedium)
                    }
                }

                Button(
                    onClick = onTogglePlayAudio,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    shape = RoundedCornerShape(999.dp),
                    modifier = Modifier.fillMaxWidth().height(44.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (state.isPlayingAudio) "Arrêter l'écoute vocale" else "Écouter la synthèse (Éwé / Mina)",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }

        // 2. Medical Document Canvas: Official Pediatric Form Sheet (République Togolaise)
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Top Security Accent Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(99.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer)
                )

                // Official Header - République Togolaise
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("TG", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                        }
                        Column {
                            Text(
                                text = "RÉPUBLIQUE TOGOLAISE",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Ministère de la Santé, de l'Hygiène Publique et de la Couverture Universelle",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "CMS ADIDOGOMÉ • Service de Pédiatrie & SMI",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = "FICHE OFFICIELLE N° TG-LOM-2024-8842",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Émis le : 20/11/2024 • 10:42 • Chiffrement AES-256 Hors-ligne",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Document Title Hero Banner
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "PASSEPORT DE SANTÉ DU NOURRISSON",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Relevé de consultation clinique mensuelle & surveillance de croissance",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                // Section 1: Identification de l'Enfant & Accompagnant
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "1. Identification de l'Enfant & Accompagnant",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text("ID: #AK-8842", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }

                        Surface(
                            color = MaterialTheme.colorScheme.surfaceContainerLowest,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "${child?.name ?: "Amel K."} • ${child?.gender ?: "Fille"} • ${child?.ageMonths ?: 8} mois (Née le ${child?.birthDate ?: "21 Mai 2024"})",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Lieu de naissance : ${child?.birthPlace ?: "Clinique Maternité Lomé Commune"}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "Mère : ${state.profile.name} • ${state.profile.phone} (Togocom) • ${state.profile.zone}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Praticienne : Dr. Akouavi Mensah",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Surface(
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(999.dp)
                            ) {
                                Text(
                                    text = "Sage-femme Clinicienne SMI",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                }

                // Section 2: Mesures Somatométriques & Nutrition
                Text(
                    text = "2. Mesures Somatométriques & Nutrition (Courbe OMS Normale)",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PassportMetricChip("Poids actuel", "${String.format("%.3f", child?.weightKg ?: 7.850f)} kg", "+${child?.weightDeltaGrams ?: 320}g (P50)", Modifier.weight(1f))
                    PassportMetricChip("Taille", "${child?.heightCm ?: 68} cm", "Conforme (P52)", Modifier.weight(1f))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PassportMetricChip("Périm. Crânien", "${child?.headCircumferenceCm ?: 44} cm", "Harmonieux", Modifier.weight(1f))
                    PassportMetricChip("Température", "${child?.temperatureCelsius ?: 37.2f} °C", "Apyrétique", Modifier.weight(1f))
                }

                OmsGrowthChartCanvas(currentWeightKg = child?.weightKg ?: 7.85f)

                Surface(
                    color = SecondaryFixed.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(Icons.Default.Restaurant, contentDescription = null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(18.dp))
                        Text(
                            text = "Orientation nutritionnelle : Bonne diversification alimentaire. Recommandation confirmée pour la bouillie enrichie locale (farine de maïs jaune, soja torréfié et poudre de feuilles de moringa).",
                            style = MaterialTheme.typography.bodySmall,
                            color = OnSecondaryFixed
                        )
                    }
                }

                // Section 3: PEV Togo
                Text(
                    text = "3. Programme Élargi de Vaccination (PEV Togo) • 4/5 Effectués (80%)",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "✓ Pentavalent 2 & VPO 2 (Polio Oral) — Administré le 18 Octobre 2024 • Lot: #PNT-992-TG (Validé CMS)",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "⏰ Prochain : Pentavalent 3 & Rougeole-Rubéole 1 (RR1) — Échéance : 16 Décembre 2024 (Dans 26 jours)",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Vitamine A (100.000 UI) : À jour • Déparasitage Albendazole : Prévu aux 12 mois",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Section 4: Prescriptions & Cryptographic QR + Stamp
                Text(
                    text = "4. Prescriptions & Recommandations Praticienne",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text("1. Hydratation SRO préventive : Diluer 1 sachet de SRO dans 1 litre d'eau bouillie refroidie.", style = MaterialTheme.typography.bodySmall)
                        Text("2. Surveillance de la température : Mesure axillaire matin et soir. Pagne léger en coton aéré.", style = MaterialTheme.typography.bodySmall)
                        Text(
                            text = "! Critères d'alerte 24h/24 : Se rendre sans délai au CMS Adidogomé si fièvre > 38,5°C ou vomissements répétés.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // QR Scan Express CMS & Digital Stamp
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            QrCodeMatrixBox(modifier = Modifier.size(64.dp))
                            Column {
                                Text("Scan Express CMS", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                                Text("SHA256: 7f8a9d...c084e1", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                            }
                        }

                        Surface(
                            color = MaterialTheme.colorScheme.surfaceContainerLowest,
                            shape = RoundedCornerShape(10.dp),
                            shadowElevation = 1.dp
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("CMS ADIDOGOMÉ", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                                Text("VALIDÉ LE 20/11/2024", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                Text("Dr. Akouavi MENSAH", style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic))
                                Text("Matricule TG-MED-4912", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = onBack,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth().height(48.dp)
        ) {
            Text("Retour au carnet d'${child?.name ?: "Amel"}", style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
private fun PassportMetricChip(
    label: String,
    value: String,
    sub: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            Text(sub, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
        }
    }
}
