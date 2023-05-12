package com.example.carros

data class carros (
    var id_TipoDeMarcas: Int,
    var descricao:String,
    var nome:String,
    var ano: Long,
    var id: Long = -1){
}