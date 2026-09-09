package com.idlecoding.repository

import com.idlecoding.data.db.dao.GlobalStateDao
import com.idlecoding.data.model.GlobalState
import com.idlecoding.data.model.GlobalStateKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlobalStateRepository @Inject constructor(
    private val dao: GlobalStateDao,
) {
    suspend fun isOnboardingComplete(): Boolean =
        dao.getValue(GlobalStateKey.ONBOARDING_COMPLETE) == "true"

    suspend fun markOnboardingComplete() {
        dao.setValue(GlobalState(
            key       = GlobalStateKey.ONBOARDING_COMPLETE,
            value     = "true",
            updatedAt = System.currentTimeMillis(),
        ))
    }

    suspend fun clearOnboardingComplete() {
        dao.delete(GlobalStateKey.ONBOARDING_COMPLETE)
    }

    suspend fun getActiveSaveSlot(): Int =
        dao.getValue(GlobalStateKey.ACTIVE_SAVE_SLOT)?.toIntOrNull() ?: 1

    suspend fun setActiveSaveSlot(slot: Int) {
        dao.setValue(GlobalState(
            key       = GlobalStateKey.ACTIVE_SAVE_SLOT,
            value     = slot.toString(),
            updatedAt = System.currentTimeMillis(),
        ))
    }
}
