package com.duoc.app.data.repository

import android.content.Context
import com.duoc.app.data.remote.client.RetrofitClient

class AppContainer(context: Context) {

    private val appContext: Context = context.applicationContext

    val retrofitClient: RetrofitClient by lazy { RetrofitClient(appContext) }
    val db: AppDatabase by lazy { AppDatabase.getDatabase(appContext) }
    val sesionManager: SesionManager by lazy { SesionManager(appContext) }
    val preferences: UserPreferences by lazy { UserPreferences(appContext) }

    val documentRepository: DocumentRepository by lazy {
        NetworkDocumentRepository(db.documentDao(), retrofitClient.documentApi)
    }
    val authRepository: AuthRepository by lazy {
        AuthRepository(retrofitClient.authApi, sesionManager)
    }
    val changesRepository: LegislativeChangeRepository by lazy {
        NetworkLegislativeChangeRepository(
            api = retrofitClient.changeApi,
            dao = db.legislativeChangeDao()
        )
    }
    val noteRepository: NoteRepository by lazy {
        NoteRepository(
            noteDao = db.noteDao(),
            noteApi = retrofitClient.noteApi
        )
    }
    val usuarioLocalRepository: UsuarioLocalRepository by lazy {
        UsuarioLocalRepository(appContext)
    }
}