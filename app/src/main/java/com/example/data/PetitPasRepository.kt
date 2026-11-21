package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

class PetitPasRepository(private val dao: PetitPasDao) {

    val userProfile: Flow<UserProfileEntity?> = dao.getUserProfile()
    val allChildren: Flow<List<ChildEntity>> = dao.getAllChildren()
    val allMemos: Flow<List<ConsultationMemoEntity>> = dao.getAllMemos()
    val allVaccines: Flow<List<VaccineRecordEntity>> = dao.getAllVaccines()
    val allMilestones: Flow<List<MilestoneActivityEntity>> = dao.getAllMilestones()

    suspend fun ensureSeedData() {
        val currentProfile = dao.getUserProfile().firstOrNull()
        if (currentProfile == null) {
            dao.upsertUserProfile(
                UserProfileEntity(
                    id = 1,
                    name = "Patricia K.",
                    role = "Parent / Famille",
                    phone = "+228 90 84 42 18",
                    isAnonymous = false,
                    zone = "Tokoin-Solidarité, Lomé",
                    preferredLanguage = "fr",
                    voiceSpeed = 0.8f,
                    isDarkMode = false,
                    isRegistered = true
                )
            )
        }

        val currentChildren = dao.getAllChildren().firstOrNull().orEmpty()
        val amelId: Int
        if (currentChildren.isEmpty()) {
            amelId = dao.insertChild(
                ChildEntity(
                    name = "Amel K.",
                    ageMonths = 8,
                    gender = "Fille",
                    birthDate = "21 Mai 2024",
                    birthPlace = "Clinique Maternité Lomé Commune",
                    weightKg = 7.85f,
                    weightDeltaGrams = 320,
                    heightCm = 68,
                    headCircumferenceCm = 44,
                    temperatureCelsius = 37.2f,
                    lastVaccine = "Pentavalent 2 & Polio 2",
                    lastVaccineDate = "18 Octobre 2024",
                    nextVaccine = "Pentavalent 3 & Rougeole",
                    nextVaccineDays = 26,
                    vaccinesCompleted = 4,
                    vaccinesTotal = 5,
                    isActive = true
                )
            ).toInt()
        } else {
            amelId = currentChildren.first().id
        }

        val currentMemos = dao.getAllMemos().firstOrNull().orEmpty()
        if (currentMemos.isEmpty()) {
            dao.insertMemo(
                ConsultationMemoEntity(
                    childId = amelId,
                    doctorName = "Dr. Akouavi Mensah",
                    doctorRole = "Sage-femme clinicienne • CMS Adidogomé",
                    cmsName = "CMS Adidogomé",
                    timestampLabel = "Aujourd'hui à 10:42",
                    durationLabel = "4 min 12s",
                    quoteFr = "Bonne prise de poids ce mois-ci. Continuer la diversification avec la bouillie enrichie togolaise (soja, maïs, moringa). Bien hydrater bébé l'après-midi.",
                    quoteEwe = "Wò dɔwɔwɔa dze edzi nyuie le wò kaƒomɔ me ! Nya sia nya si nèŋlɔ ɖe Amel ƒe lamedodo-gbalẽ me la le dedie. Ne èyi CMS Adidogomé la, sage-femme la ate ŋu akpɔe kaba.",
                    quoteKabiye = "Hezɩ ña-laŋlɛ. Amel kʊdɔŋ takayaɣ wɛ dedie ña-kaƒomɔ taa. CMS Adidogomé ɖɔkɔtɔ naɣ-kɛ kɛlɛʊ.",
                    isSavedToCarnet = true,
                    reminderScheduledTime = null
                )
            )
        }

        val currentVaccines = dao.getAllVaccines().firstOrNull().orEmpty()
        if (currentVaccines.isEmpty()) {
            val defaultVaccines = listOf(
                VaccineRecordEntity(
                    childId = amelId,
                    title = "BCG & Polio 0 (Naissance)",
                    ageTarget = "À la naissance",
                    dateAdministered = "22 Mai 2024",
                    lotNumber = "#BCG-204-TG",
                    cmsName = "Clinique Maternité Lomé",
                    isCompleted = true
                ),
                VaccineRecordEntity(
                    childId = amelId,
                    title = "Pentavalent 1 & VPO 1 + Pneumo 1",
                    ageTarget = "6 semaines (1,5 mois)",
                    dateAdministered = "08 Juillet 2024",
                    lotNumber = "#PNT-418-TG",
                    cmsName = "CMS Adidogomé",
                    isCompleted = true
                ),
                VaccineRecordEntity(
                    childId = amelId,
                    title = "Pentavalent 2 & VPO 2 (Polio Oral)",
                    ageTarget = "10 semaines / Rappel",
                    dateAdministered = "18 Octobre 2024",
                    lotNumber = "#PNT-992-TG",
                    cmsName = "CMS Adidogomé • Dr. Akouavi",
                    isCompleted = true
                ),
                VaccineRecordEntity(
                    childId = amelId,
                    title = "Vitamine A (100.000 UI)",
                    ageTarget = "6 mois",
                    dateAdministered = "18 Novembre 2024",
                    lotNumber = "#VITA-108-TG",
                    cmsName = "CMS Adidogomé",
                    isCompleted = true
                ),
                VaccineRecordEntity(
                    childId = amelId,
                    title = "Pentavalent 3 & Rougeole-Rubéole 1 (RR1)",
                    ageTarget = "9 mois (Dans 26 jours)",
                    dateAdministered = "Échéance : 16 Décembre 2024",
                    lotNumber = "En attente CMS",
                    cmsName = "CMS Adidogomé",
                    isCompleted = false
                )
            )
            defaultVaccines.forEach { dao.insertVaccine(it) }
        }

        val currentMilestones = dao.getAllMilestones().firstOrNull().orEmpty()
        if (currentMilestones.isEmpty()) {
            val defaultMilestones = listOf(
                MilestoneActivityEntity(
                    category = "Nutrition locale",
                    titleFr = "Bouillie enrichie Maïs-Soja-Moringa",
                    titleEwe = "Dzogbɔ си wotsɔ Moringa kple Soja de eme",
                    descriptionFr = "Mélanger 2 cuillères de farine de maïs jaune + 1 cuillère de soja torréfié + 1 pincée de poudre de feuilles de moringa. Cuire 10 min à l'eau propre.",
                    audioScriptEwe = "Ða dzogbɔ nyui na Amel kple bli, soja kple yevutsi (moringa) ƒe aŋgba.",
                    ageLabel = "Dès 6–8 mois",
                    isCompleted = true
                ),
                MilestoneActivityEntity(
                    category = "Motricité",
                    titleFr = "Position assise stable sur la natte",
                    titleEwe = "Bɔbɔ nɔ anyi ɖe aba dzi dedie",
                    descriptionFr = "Amel tient assise sans appui pendant plusieurs minutes et attrape des objets colorés des deux mains.",
                    audioScriptEwe = "Amel te ŋu bɔbɔ nɔa anyi ɖe aba dzi eye wòléa fefewɔnuwo.",
                    ageLabel = "7–8 mois",
                    isCompleted = true
                ),
                MilestoneActivityEntity(
                    category = "Langage & Éveil",
                    titleFr = "Jeu de coucou & babillage bilingue",
                    titleEwe = "Nuƒoƒo kple nukoko kple dada",
                    descriptionFr = "Chanter une berceuse en Éwé ou Français, répondre à ses syllabes (« ba-ba », « ma-ma ») avec le sourire.",
                    audioScriptEwe = "Dzi ha na viwò le Eʋegbe alo Fransegbe me ne wòakpɔ dzidzɔ.",
                    ageLabel = "8–9 mois",
                    isCompleted = false
                ),
                MilestoneActivityEntity(
                    category = "Nutrition locale",
                    titleFr = "Purée douce d'igname & poisson sec pilé",
                    titleEwe = "Te memee kple tɔmelã ƒuƒu",
                    descriptionFr = "Écraser finement un morceau d'igname bien cuit avec une demi-cuillère de poudre de petits poissons secs riches en calcium.",
                    audioScriptEwe = "Tu te memee kple tɔmelã ƒuƒu sẽe na Amel ne eƒe ƒuwo nasẽ.",
                    ageLabel = "8–10 mois",
                    isCompleted = false
                )
            )
            defaultMilestones.forEach { dao.insertMilestone(it) }
        }
    }

    suspend fun updateProfile(profile: UserProfileEntity) = dao.upsertUserProfile(profile)

    suspend fun addChild(child: ChildEntity) {
        val newId = dao.insertChild(child).toInt()
        dao.setActiveChild(newId)
    }

    suspend fun updateChild(child: ChildEntity) = dao.updateChild(child)

    suspend fun selectActiveChild(childId: Int) = dao.setActiveChild(childId)

    suspend fun updateMemo(memo: ConsultationMemoEntity) = dao.updateMemo(memo)

    suspend fun toggleVaccineCompleted(vaccine: VaccineRecordEntity, activeChild: ChildEntity?) {
        val updated = vaccine.copy(
            isCompleted = !vaccine.isCompleted,
            dateAdministered = if (!vaccine.isCompleted) "Validé aujourd'hui au CMS" else "En attente CMS"
        )
        dao.updateVaccine(updated)
        if (activeChild != null) {
            val allForChild = dao.getVaccinesForChild(activeChild.id).firstOrNull().orEmpty()
            val completedCount = allForChild.count { it.isCompleted }
            dao.updateChild(
                activeChild.copy(
                    vaccinesCompleted = completedCount,
                    vaccinesTotal = allForChild.size.coerceAtLeast(5)
                )
            )
        }
    }

    suspend fun toggleMilestone(milestone: MilestoneActivityEntity) {
        dao.updateMilestone(milestone.copy(isCompleted = !milestone.isCompleted))
    }
}
