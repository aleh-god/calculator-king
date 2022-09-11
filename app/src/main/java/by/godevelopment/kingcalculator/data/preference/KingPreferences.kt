package by.godevelopment.kingcalculator.data.preference

//import android.content.Context
//import androidx.core.content.edit
//import dagger.hilt.android.qualifiers.ApplicationContext
//import javax.inject.Inject
//
//class KingPreferences @Inject constructor(@ApplicationContext appContext: Context) {
//
//    private val sharedPreferences = appContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
//
//    fun setCurrentPartyId(partyId: Long) {
//        sharedPreferences.edit {
//            putLong(PREF_CURRENT_PARTY_KEY, partyId)
//        }
//    }
//
//    fun getCurrentPartyId(): Long = sharedPreferences.getLong(PREF_CURRENT_PARTY_KEY, NO_PARTY_KEY)
//
//    companion object {
//        private const val PREFERENCE_NAME = "king_pref"
//        private const val PREF_CURRENT_PARTY_KEY = "current_party"
//        private const val NO_PARTY_KEY = -1L
//    }
//}
