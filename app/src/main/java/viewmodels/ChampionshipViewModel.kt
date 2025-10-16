package com.example.monclassementfoot.viewmodels

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel



// Nouveau modèle pour stocker la catégorie d’un championnat
data class ChampionnatInfo(
    val nom: String,
    val categorie: String
)

class ChampionshipViewModel : ViewModel() {

    // Stockage des équipes par championnat
    private val championnatMap = mutableStateMapOf<String, MutableList<Equipe>>()

    // Stockage des infos du championnat (catégorie)
    private val championnatInfoMap = mutableStateMapOf<String, ChampionnatInfo>()

    // Ajouter une équipe à un championnat
    fun ajouterEquipe(nomChampionnat: String, equipe: Equipe) {
        val listeEquipes = championnatMap.getOrPut(nomChampionnat) { mutableListOf() }
        listeEquipes.add(equipe)
    }

    // Récupérer la liste des équipes d’un championnat
    fun getEquipes(nomChampionnat: String): List<Equipe> {
        return championnatMap[nomChampionnat]?.toList() ?: emptyList()
    }

    // Créer un championnat avec catégorie
    fun creerChampionnat(nomChampionnat: String, categorie: String) {
        if (!championnatMap.containsKey(nomChampionnat)) {
            championnatMap[nomChampionnat] = mutableListOf()
            championnatInfoMap[nomChampionnat] = ChampionnatInfo(nomChampionnat, categorie)
        }
    }

    // Récupérer la catégorie d’un championnat
    fun getCategorie(nomChampionnat: String): String {
        return championnatInfoMap[nomChampionnat]?.categorie ?: ""
    }

    // Récupérer tous les championnats existants
    fun getChampionnats(): List<String> {
        return championnatMap.keys.toList()
    }
}
