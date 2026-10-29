
package com.duoc.app.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.duoc.app.data.model.UsuarioEntity

@Dao
interface UsuarioDao {

    @Insert
    suspend fun registrarUsuario(usuario: UsuarioEntity): Long

    @Query("SELECT * FROM usuarios WHERE correo = :correo LIMIT 1")
    suspend fun buscarPorCorreo(correo: String): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Int): UsuarioEntity?
}
