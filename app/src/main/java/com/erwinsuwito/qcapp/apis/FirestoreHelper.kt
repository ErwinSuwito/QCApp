package com.erwinsuwito.qcapp.apis

import android.app.Activity
import com.erwinsuwito.qcapp.model.*
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreHelper {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun addIssue(activity: Activity, issue: Issue)
    {
        mFireStore.collection("issues")
                .add(issue)
                .addOnSuccessListener {
                    when (activity)
                    {
                        // TO-DO: Add actions here
                        /*
                        is LoginActivity -> {
                            activity.userLoggedInSuccess(user)
                        }
                         */
                    }
                }
                .addOnFailureListener {
                    when (activity)
                    {
                        // TO-DO: Add actions here
                        /*
                        is LoginActivity -> {
                            activity.userLoggedInSuccess(user)
                        }
                         */
                    }
                }
    }

    fun addSteps(activity: Activity, issue: Issue, step: Steps)
    {
        mFireStore.collection("issues")
                .document(issue.issueId)
                .collection("steps")
                .add(step)
                .addOnSuccessListener {
                    when (activity)
                    {
                        // TO-DO: Add actions here
                        /*
                        is LoginActivity -> {
                            activity.userLoggedInSuccess(user)
                        }
                         */
                    }
                }
                .addOnFailureListener {
                    when (activity)
                    {
                        // TO-DO: Add actions here
                        /*
                        is LoginActivity -> {
                            activity.userLoggedInSuccess(user)
                        }
                         */
                    }
                }
    }

    fun addChecks(activity: Activity, check: ClassCheck) {
        mFireStore.collection("checks")
                .add(check)
                .addOnSuccessListener {
                    when (activity)
                    {
                        // TO-DO: Add actions here
                        /*
                        is LoginActivity -> {
                            activity.userLoggedInSuccess(user)
                        }
                         */
                    }
                }
                .addOnFailureListener {
                    when (activity)
                    {
                        // TO-DO: Add actions here
                        /*
                        is LoginActivity -> {
                            activity.userLoggedInSuccess(user)
                        }
                         */
                    }
                }
    }

    fun addClass(activity: Activity, classroom: Classroom) {
        mFireStore.collection("classes")
                .add(classroom)
                .addOnSuccessListener {
                    when (activity)
                    {
                        // TO-DO: Add actions here
                        /*
                        is LoginActivity -> {
                            activity.userLoggedInSuccess(user)
                        }
                         */
                    }
                }
                .addOnFailureListener {
                    when (activity)
                    {
                        // TO-DO: Add actions here
                        /*
                        is LoginActivity -> {
                            activity.userLoggedInSuccess(user)
                        }
                         */
                    }
                }
    }
}