package com.abadock.examenuf2

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class assignatura(
    val nom: String
)

data class assignatura2(
    val nom: String,
    val codi: String
)

data class alumne(
    val nom:String
)

data class nota(
    val nomAssign:String,
    val nota:Int
)

class ListViewModel : ViewModel() {

    //Obté totes les assignatures que tenen més de 50 hores.

    fun assigna50h() {
        val db = Firebase.firestore

        val assignatures = mutableListOf<assignatura>()
        db.collection("Assignatures")
            .whereGreaterThan("Hores totals", 50)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    assignatures.add(assignatura(document.data["nom"].toString()))
                }
            }.addOnCompleteListener{
                Log.d("ExamenUF2", assignatures.toString())
            }

        //live data amb la variable assignatures
    }

    //Mostra el nom i el codi de les assignatures que tenen com a professor
    //responsable algú amb l'especialitat de "Matemàtiques".

    fun assignaProfeMates() {
        val db = Firebase.firestore

        val assignatures = mutableListOf<assignatura2>()
        db.collection("Professor")
            .whereEqualTo("Especialitat", "Matemàtiques")
            .get()
            .addOnSuccessListener { profes ->
                for (profe in profes) {
                    db.collection("Assignatures")
                        .whereEqualTo("nom", profe.data["Assignatura"])
                        .get()
                        .addOnSuccessListener { assginaFB ->
                            for (assgin in assginaFB) {
                                assignatures.add(assignatura2(assgin.id, assgin.data["nom"].toString()))
                            }
                        }
                        .addOnCompleteListener{
                            Log.d("ExamenUF2", assignatures.toString())
                        }
                }
            }
        //live data amb la variable assignatures
    }

    // Obté una llista dels alumnes matriculats en una assignatura específica
    //(que vingui informada per una variable, per exemple), utilitzant el seu codi.

    fun getAlumnesAssigna(assignatura: String){
        val db = Firebase.firestore

        val llistaAlumnes = mutableListOf<alumne>()
        db.collection("Alumnes")
            .whereArrayContains("Matrícules", assignatura)
            .get()
            .addOnSuccessListener { alumnes ->
                for (alumne in alumnes) {
                    llistaAlumnes.add(alumne(alumne.data["Nom"].toString()))
                }
            }.addOnCompleteListener{
                Log.d("ExamenUF2", llistaAlumnes.toString())
            }
        //live data amb la variable assignatures
    }

    //Obté totes les notes finals d'un alumne donat, utilitzant el codi de l'alumne.

    fun getNotesAlumne(codiAlumne: String){
        val db = Firebase.firestore

        val llistaNotes = mutableListOf<nota>()
        db.collection("Notes")
            .whereEqualTo("codiAlumne", codiAlumne)
            .get()
            .addOnSuccessListener { documents ->
                for (doc in documents){
                    llistaNotes.add(nota(doc.data["assignatura"].toString(), doc.data["nota"].toString().toInt()))
                }
            }.addOnCompleteListener{
                Log.d("ExamenUF2", llistaNotes.toString())
            }
        //live data amb la variable assignatures
    }


    //Afegeix un nou professor.
    fun newTeacher(nom: String, Cognom: String, dni: String, Especialitat: String, mail: String) {
        val db = Firebase.firestore

        val data = hashMapOf(
            "Nom" to nom,
            "Cognom" to Cognom,
            "DNI" to dni,
            "Especialitat" to Especialitat,
            "mail" to mail
        )

        db.collection("Professor").add(data).addOnSuccessListener {
            Log.d("ExamenUF2", "Profe afegit")
        }.addOnFailureListener{
            Log.d("ExamenUF2", "Error a l'hora de afegir profe")
        }
    }
}




