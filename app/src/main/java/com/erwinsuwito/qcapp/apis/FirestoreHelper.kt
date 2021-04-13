package com.erwinsuwito.qcapp.apis

import com.erwinsuwito.qcapp.model.Issue
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreHelper {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun addIssue(issue: Issue) : Boolean
    {
        return false
    }
}