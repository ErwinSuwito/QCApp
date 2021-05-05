package com.erwinsuwito.qcapp.apis

import com.erwinsuwito.qcapp.model.*
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreHelper {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun addIssue(issue: Issue, onSuccess: () -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection("issues")
                .add(issue)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure()
                }
    }

    fun addSteps(issue: Issue, step: Steps, onSuccess: () -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection("issues")
                .document(issue.issueId)
                .collection("steps")
                .add(step)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure()
                }
    }

    fun addSteps(task: Task, step: Steps, onSuccess: () -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection("tasks")
                .document(task.issueId)
                .collection("steps")
                .add(step)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure()
                }
    }

    fun addChecks(check: ClassCheck, onSuccess: () -> Unit, onFailure: () -> Unit) {
        mFireStore.collection("checks")
                .add(check)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure()
                }
    }

    fun addClass(classroom: Classroom, onSuccess: () -> Unit, onFailure: () -> Unit) {
        mFireStore.collection("classes")
                .add(classroom)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure()
                }
    }

    fun addTask(task: Task, onSuccess: () -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection("tasks")
                .add(task)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure()
                }
    }
}