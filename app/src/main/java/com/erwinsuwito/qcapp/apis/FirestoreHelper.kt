package com.erwinsuwito.qcapp.apis

import com.erwinsuwito.qcapp.model.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.merge

class FirestoreHelper {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun addIssue(issue: Issue, onSuccess: () -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection("issues")
                .document(issue.issueId)
                .set(issue, SetOptions.merge())
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
                .document(step.stepId)
                .set(step, SetOptions.merge())
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
                .document(step.stepId)
                .set(step, SetOptions.merge())
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure()
                }
    }

    fun addChecks(check: ClassCheck, onSuccess: () -> Unit, onFailure: () -> Unit) {
        mFireStore.collection("checks")
                .document(check.checkId)
                .set(check, SetOptions.merge())
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure()
                }
    }

    fun addClass(classroom: Classroom, onSuccess: () -> Unit, onFailure: () -> Unit) {
        mFireStore.collection("classes")
                .document(classroom.className)
                .set(classroom, SetOptions.merge())
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
                .document(task.issueId)
                .set(task, SetOptions.merge())
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure()
                }
    }

    fun classList(onSuccess: (MutableList<Classroom>) -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection("classes")
                .get()
                .addOnSuccessListener {
                    var classes = mutableListOf<Classroom>()

                    for (i in it.documents) {
                        val classroom = i.toObject(Classroom::class.java)
                        if (classroom != null)
                        {
                            classes.add(classroom)
                        }
                    }

                    onSuccess(classes)
                }
                .addOnFailureListener {
                    onFailure()
                }
    }
}