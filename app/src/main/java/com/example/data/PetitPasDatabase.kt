package com.example.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "Augustine A.",
    val role: String = "Parent / Famille",
    val phone: String = "+228 90 84 42 18",
    val isAnonymous: Boolean = false,
    val zone: String = "Tokoin-Solidarité, Lomé",
    val preferredLanguage: String = "fr", // "fr", "ewe", "kabiye", "en"
    val voiceSpeed: Float = 0.8f,
    val isDarkMode: Boolean = false,
    val isRegistered: Boolean = true
)

@Entity(tableName = "children")
data class ChildEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val ageMonths: Int,
    val gender: String,
    val birthDate: String,
    val birthPlace: String,
    val weightKg: Float,
    val weightDeltaGrams: Int,
    val heightCm: Int,
    val headCircumferenceCm: Int,
    val temperatureCelsius: Float,
    val lastVaccine: String,
    val lastVaccineDate: String,
    val nextVaccine: String,
    val nextVaccineDays: Int,
    val vaccinesCompleted: Int,
    val vaccinesTotal: Int,
    val isActive: Boolean = false
)

@Entity(tableName = "consultation_memos")
data class ConsultationMemoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val childId: Int,
    val doctorName: String,
    val doctorRole: String,
    val cmsName: String,
    val timestampLabel: String,
    val durationLabel: String,
    val quoteFr: String,
    val quoteEwe: String,
    val quoteKabiye: String,
    val isSavedToCarnet: Boolean = true,
    val reminderScheduledTime: String? = null
)

@Entity(tableName = "vaccine_records")
data class VaccineRecordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val childId: Int,
    val title: String,
    val ageTarget: String,
    val dateAdministered: String,
    val lotNumber: String,
    val cmsName: String,
    val isCompleted: Boolean
)

@Entity(tableName = "milestone_activities")
data class MilestoneActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String, // "Nutrition locale", "Motricité", "Langage & Éveil"
    val titleFr: String,
    val titleEwe: String,
    val descriptionFr: String,
    val audioScriptEwe: String,
    val ageLabel: String,
    val isCompleted: Boolean
)

@Dao
interface PetitPasDao {
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUserProfile(profile: UserProfileEntity)

    @Query("SELECT * FROM children ORDER BY isActive DESC, id ASC")
    fun getAllChildren(): Flow<List<ChildEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChild(child: ChildEntity): Long

    @Update
    suspend fun updateChild(child: ChildEntity)

    @Query("UPDATE children SET isActive = CASE WHEN id = :childId THEN 1 ELSE 0 END")
    suspend fun setActiveChild(childId: Int)

    @Query("SELECT * FROM consultation_memos ORDER BY id DESC")
    fun getAllMemos(): Flow<List<ConsultationMemoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemo(memo: ConsultationMemoEntity)

    @Update
    suspend fun updateMemo(memo: ConsultationMemoEntity)

    @Query("SELECT * FROM vaccine_records WHERE childId = :childId ORDER BY id ASC")
    fun getVaccinesForChild(childId: Int): Flow<List<VaccineRecordEntity>>

    @Query("SELECT * FROM vaccine_records ORDER BY id ASC")
    fun getAllVaccines(): Flow<List<VaccineRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVaccine(vaccine: VaccineRecordEntity)

    @Update
    suspend fun updateVaccine(vaccine: VaccineRecordEntity)

    @Query("SELECT * FROM milestone_activities ORDER BY id ASC")
    fun getAllMilestones(): Flow<List<MilestoneActivityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMilestone(milestone: MilestoneActivityEntity)

    @Update
    suspend fun updateMilestone(milestone: MilestoneActivityEntity)
}

@Database(
    entities = [
        UserProfileEntity::class,
        ChildEntity::class,
        ConsultationMemoEntity::class,
        VaccineRecordEntity::class,
        MilestoneActivityEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PetitPasDatabase : RoomDatabase() {
    abstract fun dao(): PetitPasDao

    companion object {
        @Volatile
        private var INSTANCE: PetitPasDatabase? = null

        fun getDatabase(context: Context): PetitPasDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PetitPasDatabase::class.java,
                    "petit_pas_offline_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
