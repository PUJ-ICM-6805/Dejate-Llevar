package com.example.dejatellevar

data class Servicio(
    val idServicio: String, // Identificador único del servicio
    val idEmpresa: String, // Identificador de la empresa relacionada
    val nombre: String, // Nombre del servicio
    val tipos: List<String>, // Lista de tipos (restaurante, desayuno, etc.)
    val descripcion: String, // Descripción del servicio
    val precio: Double, // Precio del servicio
    val etiquetas: List<String>, // Etiquetas asociadas al servicio
    val horario: List<String>, // Lista de horarios (puede ser de tipo Date)
    val comentario: String, // Comentario o descripción adicional
    val tiktokerPromocion: String, // Nombre del tiktoker de promoción
    val reel: String, // Enlace al reel promocional
    val foto: String, // URL de la foto del servicio
    val video: String, // URL de un video promocional
    val estadoReserva: Boolean // Estado de la reserva (true si está disponible, false si no)
)

