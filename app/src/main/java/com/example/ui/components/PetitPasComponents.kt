package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.PrimaryFixed
import com.example.ui.theme.SecondaryContainerLight
import com.example.ui.theme.SecondaryFixed

@Composable
fun PetitPasTopBar(
    title: String,
    subtitle: String? = "Hors ligne OK",
    showBackButton: Boolean = false,
    isDarkMode: Boolean = false,
    showLanguageDrawer: Boolean = false,
    currentLanguage: String = "fr",
    onBackClick: () -> Unit = {},
    onMenuOrLangClick: () -> Unit = {},
    onToggleDarkMode: () -> Unit = {},
    onVoiceAssistClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onSelectLanguage: (String) -> Unit = {}
) {
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f),
        shadowElevation = 3.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.statusBarsPadding()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    if (showBackButton) {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .size(48.dp)
                                .testTag("top_bar_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Retour",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    IconButton(
                        onClick = onMenuOrLangClick,
                        modifier = Modifier
                            .size(48.dp)
                            .testTag("top_bar_menu_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu 3 barres et Langues",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (subtitle != null) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(7.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primary)
                                )
                                Text(
                                    text = subtitle,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    IconButton(
                        onClick = onToggleDarkMode,
                        modifier = Modifier
                            .size(48.dp)
                            .testTag("toggle_theme_button")
                    ) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Basculer mode sombre/clair",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(
                        onClick = onVoiceAssistClick,
                        modifier = Modifier
                            .size(48.dp)
                            .testTag("top_voice_assist_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.RecordVoiceOver,
                            contentDescription = "Assistance Vocale",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                            .clickable(onClick = onProfileClick)
                            .testTag("top_profile_button"),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.avatar_patricia),
                            contentDescription = "Mon Profil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            AnimatedVisibility(visible = showLanguageDrawer) {
                LanguageDrawerSheet(
                    currentLanguage = currentLanguage,
                    onSelectLanguage = onSelectLanguage,
                    onClose = onMenuOrLangClick
                )
            }
        }
    }
}

@Composable
fun LanguageDrawerSheet(
    currentLanguage: String,
    onSelectLanguage: (String) -> Unit,
    onClose: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        tonalElevation = 4.dp,
        shadowElevation = 6.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
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
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Choisir la langue audio",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onClose, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Fermer",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            LanguageSelectorGrid(
                currentLanguage = currentLanguage,
                onSelectLanguage = onSelectLanguage
            )
        }
    }
}

@Composable
fun LanguageSelectorGrid(
    currentLanguage: String,
    onSelectLanguage: (String) -> Unit
) {
    val langs = listOf(
        Triple("fr", "Français", if (currentLanguage == "fr") "Voix active" else "Français"),
        Triple("ewe", "Eʋegbe", if (currentLanguage == "ewe") "Voix active" else "Éwé"),
        Triple("kabiye", "Kabɩyɛ", if (currentLanguage == "kabiye") "Voix active" else "Kabyè"),
        Triple("en", "English", if (currentLanguage == "en") "Voix active" else "Audio guide")
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        for (row in langs.chunked(2)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for ((code, title, subtitle) in row) {
                    val isSelected = currentLanguage == code
                    val bgColor = if (isSelected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surfaceContainerHigh
                    }
                    val contentColor = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                    val subColor = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    }

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(bgColor)
                            .clickable { onSelectLanguage(code) }
                            .padding(horizontal = 12.dp)
                            .testTag("lang_pill_$code"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelLarge,
                                color = contentColor
                            )
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.labelSmall,
                                color = subColor
                            )
                        }
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Écouter en $title",
                            tint = if (isSelected) contentColor else MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OfflineStatusPill(
    text: String = "● En ligne & Hors connexion : OK • Mémoire locale",
    trailingBadge: String? = null
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        shape = RoundedCornerShape(999.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (trailingBadge != null) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerLowest,
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text(
                        text = trailingBadge,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Default.CloudDone,
                    contentDescription = "Stockage local protégé",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun PetitPasHeroBrandBanner(
    onListenWelcome: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Official PETIT PAS Brand Illustration Banner — displayed freely without circle or border
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF8F9FA), RoundedCornerShape(12.dp))
                    .padding(vertical = 10.dp, horizontal = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_petit_pas),
                    contentDescription = "Logo officiel Petit Pas - Chaque petit pas compte.",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(184.dp)
                        .testTag("hero_logo_petit_pas")
                )
            }

            // Bottom Greeting Row + "Écouter" Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Bienvenue sur votre carnet bienveillant",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                    shape = RoundedCornerShape(999.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(999.dp))
                        .clickable(onClick = onListenWelcome)
                        .testTag("btn_voice_welcome")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Écouter le message de bienvenue",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Écouter",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OmsGrowthChartCanvas(
    currentWeightKg: Float = 7.85f,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Courbe OMS (0 à 8 mois)",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Percentile p50 • Objectif atteint",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        ) {
            val w = size.width
            val h = size.height

            // WHO Healthy Zone Corridor (Soft Emerald fill)
            val corridorPath = Path().apply {
                moveTo(0f, h * 0.85f)
                quadraticTo(w * 0.45f, h * 0.52f, w, h * 0.14f)
                lineTo(w, h * 0.42f)
                quadraticTo(w * 0.45f, h * 0.72f, 0f, h * 0.96f)
                close()
            }
            drawPath(
                path = corridorPath,
                color = Color(0xFF91D3C8).copy(alpha = 0.32f)
            )

            // Reference WHO Median Dashed Curve
            val medianPath = Path().apply {
                moveTo(0f, h * 0.90f)
                quadraticTo(w * 0.45f, h * 0.60f, w, h * 0.26f)
            }
            drawPath(
                path = medianPath,
                color = Color(0xFF135D54).copy(alpha = 0.45f),
                style = Stroke(
                    width = 4f,
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 8f), 0f)
                )
            )

            // Child's Actual Growth Curve (Bold Deep Emerald)
            val childPath = Path().apply {
                moveTo(0f, h * 0.92f)
                lineTo(w * 0.25f, h * 0.74f)
                lineTo(w * 0.55f, h * 0.54f)
                lineTo(w * 0.78f, h * 0.38f)
                lineTo(w * 0.97f, h * 0.22f)
            }
            drawPath(
                path = childPath,
                color = Color(0xFF00443D),
                style = Stroke(width = 7f, cap = StrokeCap.Round)
            )

            // Current Measurement Point (Solar Orange with white ring)
            val endPoint = Offset(w * 0.97f, h * 0.22f)
            drawCircle(
                color = Color.White,
                radius = 13f,
                center = endPoint
            )
            drawCircle(
                color = SecondaryContainerLight,
                radius = 9f,
                center = endPoint
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Naiss: 3,1 kg", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("3m: 5,4 kg", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text("6m: 6,9 kg", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(
                text = "8m: ${String.format("%.2f", currentWeightKg)} kg",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun QrCodeMatrixBox(
    modifier: Modifier = Modifier.size(72.dp)
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 1.dp,
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val s = size.width / 10f
            val dark = Color(0xFF181C20)

            fun drawFinder(gx: Int, gy: Int) {
                drawRect(
                    color = dark,
                    topLeft = Offset(gx * s, gy * s),
                    size = Size(3 * s, 3 * s),
                    style = Stroke(width = s * 0.6f)
                )
                drawRect(
                    color = dark,
                    topLeft = Offset((gx + 0.9f) * s, (gy + 0.9f) * s),
                    size = Size(1.2f * s, 1.2f * s)
                )
            }

            drawFinder(0, 0)
            drawFinder(7, 0)
            drawFinder(0, 7)

            val dots = listOf(
                4 to 1, 5 to 1, 4 to 2, 5 to 3,
                1 to 4, 3 to 4, 4 to 4, 5 to 4, 7 to 4, 8 to 4,
                4 to 5, 5 to 5, 7 to 6, 8 to 6,
                4 to 7, 6 to 7, 6 to 8, 8 to 8, 4 to 8
            )
            dots.forEach { (x, y) ->
                drawRect(
                    color = dark,
                    topLeft = Offset(x * s, y * s),
                    size = Size(s * 0.9f, s * 0.9f)
                )
            }
        }
    }
}

@Composable
fun SoundWaveVisualizer(
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "waveform")
    val animPhase by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 550, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "wave_factor"
    )

    val baseHeights = listOf(0.25f, 0.7f, 1.0f, 0.95f, 0.65f, 1.0f, 0.85f, 1.0f, 0.3f, 0.55f, 0.5f, 0.25f, 0.4f, 0.75f)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        baseHeights.forEachIndexed { index, base ->
            val factor = if (isPlaying) {
                val mod = if (index % 2 == 0) animPhase else (1.35f - animPhase)
                (base * mod).coerceIn(0.2f, 1.0f)
            } else {
                base
            }
            val barColor = when {
                index == 2 || index == 5 || index == 8 -> MaterialTheme.colorScheme.secondary
                index < 11 -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.outlineVariant
            }
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight(factor)
                    .clip(RoundedCornerShape(99.dp))
                    .background(barColor)
            )
        }
    }
}

@Composable
fun EthicalReassuranceFooter(
    onListenClick: (() -> Unit)? = null
) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.VerifiedUser,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(22.dp)
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Outil d'accompagnement • Pas un diagnostic médical",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    if (onListenClick != null) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Écouter la mention éthique",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable(onClick = onListenClick)
                        )
                    }
                }
                Text(
                    text = "Cet outil ne remplace pas l'avis d'une sage-femme ou d'un médecin. Données strictement sauvegardées sur votre téléphone, sans collecte ni pistage internet.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
