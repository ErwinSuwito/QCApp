package com.erwinsuwito.qcapp.apis

import android.util.Log
import com.erwinsuwito.qcapp.Constants
import com.erwinsuwito.qcapp.model.*
import com.google.firebase.firestore.*

class FirestoreHelper {

    private val mFireStore = FirebaseFirestore.getInstance()

    //region Classes

    fun addClass(classroom: Classroom, onSuccess: () -> Unit, onFailure: () -> Unit) {
        mFireStore.collection(Constants.CLASSES)
                .document(classroom.classroomName)
                .set(classroom, SetOptions.merge())
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    fun getClassList(onSuccess: (MutableList<Classroom>) -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection(Constants.CLASSES)
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
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    fun getClassDetail(classId: String, onSuccess: (Classroom?) -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection(Constants.CLASSES)
                .document(classId)
                .get()
                .addOnSuccessListener {
                    onSuccess(it.toObject(Classroom::class.java))
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    //endregion

    //region Issues

    fun addIssue(issue: Issue, onSuccess: () -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection(Constants.ISSUES)
                .document(issue.issueId)
                .set(issue, SetOptions.merge())
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    fun getIssue(issueId: String, onSuccess: (Issue?) -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection(Constants.ISSUES)
                .document(issueId)
                .get()
                .addOnSuccessListener {
                    onSuccess(it.toObject(Issue::class.java))
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    fun getIssueList(onSuccess: (MutableList<Issue>) -> Unit, onFailure: () -> Unit )
    {
        mFireStore.collection(Constants.ISSUES)
                .get()
                .addOnSuccessListener {
                    var issues = mutableListOf<Issue>()

                    for (i in it.documents) {
                        val issue = i.toObject(Issue::class.java)
                        if (issue != null)
                        {
                            issues.add(issue)
                        }
                    }
                    onSuccess(issues)
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    fun getIssueList(className: String, onSuccess: (MutableList<Issue>) -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection(Constants.ISSUES)
                .whereEqualTo(Constants.CLASSID, className)
                .get()
                .addOnSuccessListener {
                    var issues = mutableListOf<Issue>()

                    for (i in it.documents) {
                        val issue = i.toObject(Issue::class.java)
                        if (issue != null)
                        {
                            issues.add(issue)
                        }
                    }
                    onSuccess(issues)
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    fun getLongRunningIssues(onSuccess: (MutableList<Issue>) -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection(Constants.ISSUES)
                .whereEqualTo(Constants.ISOPEN, true)
                .orderBy(Constants.OPENEDON, Query.Direction.ASCENDING)
                .limit(6)
                .get()
                .addOnSuccessListener {
                    var issues = mutableListOf<Issue>()

                    for (i in it.documents) {
                        val issue = i.toObject(Issue::class.java)
                        if (issue != null)
                        {
                            issues.add(issue)
                        }
                    }
                    onSuccess(issues)
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    //endregion

    //region Tasks

    fun addTask(task: Task, onSuccess: () -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection(Constants.TASKS)
                .document(task.issueId)
                .set(task, SetOptions.merge())
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    fun getTask(taskId: String, onSuccess: (Task?) -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection(Constants.TASKS)
                .document(taskId)
                .get()
                .addOnSuccessListener {
                    onSuccess(it.toObject(Task::class.java))
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    fun getTasksList(onSuccess: (MutableList<Task>) -> Unit, onFailure: () -> Unit )
    {
        mFireStore.collection(Constants.TASKS)
                .get()
                .addOnSuccessListener {
                    var tasks = mutableListOf<Task>()

                    for (i in it.documents) {
                        val task = i.toObject(Task::class.java)
                        if (task != null)
                        {
                            tasks.add(task)
                        }
                    }
                    onSuccess(tasks)
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    fun getLongRunningTasks(onSuccess: (MutableList<Task>) -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection(Constants.TASKS)
                .whereEqualTo(Constants.ISOPEN, true)
                .orderBy(Constants.OPENEDON, Query.Direction.ASCENDING)
                .limitToLast(6)
                .get()
                .addOnSuccessListener {
                    var tasks = mutableListOf<Task>()

                    for (i in it.documents) {
                        val task = i.toObject(Task::class.java)
                        if (task != null)
                        {
                            tasks.add(task)
                        }
                    }
                    onSuccess(tasks)
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    //endregion

    //region Troubleshooting steps

    fun addIssueSteps(issue: Issue, step: Steps, onSuccess: () -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection(Constants.ISSUES)
                .document(issue.issueId)
                .collection(Constants.STEPS)
                .document(step.stepId)
                .set(step, SetOptions.merge())
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    fun addTaskSteps(task: Task, step: Steps, onSuccess: () -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection(Constants.TASKS)
                .document(task.issueId)
                .collection(Constants.STEPS)
                .document(step.stepId)
                .set(step, SetOptions.merge())
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    fun getIssueSteps(issueId: String, onSuccess: (MutableList<Steps>) -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection(Constants.ISSUES)
                .whereEqualTo(Constants.ISSUEID, issueId)
                .get()
                .addOnSuccessListener {
                    var steps = mutableListOf<Steps>()

                    for (i in it.documents) {
                        val step = i.toObject(Steps::class.java)
                        if (step != null)
                        {
                            steps.add(step)
                        }
                    }

                    onSuccess(steps)
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    fun getTaskSteps(issueId: String, onSuccess: (MutableList<Steps>) -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection(Constants.TASKS)
                .whereEqualTo(Constants.ISSUEID, issueId)
                .get()
                .addOnSuccessListener {
                    var steps = mutableListOf<Steps>()

                    for (i in it.documents) {
                        val step = i.toObject(Steps::class.java)
                        if (step != null)
                        {
                            steps.add(step)
                        }
                    }

                    onSuccess(steps)
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    //endregion

    //region Checks

    fun addChecks(check: ClassCheck, onSuccess: () -> Unit, onFailure: () -> Unit) {
        mFireStore.collection(Constants.CHECKS)
                .document(check.checkId)
                .set(check, SetOptions.merge())
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    fun getCheckHistory(className: String, onSuccess: (MutableList<ClassCheck>) -> Unit, onFailure: () -> Unit) {
        mFireStore.collection(Constants.CHECKS)
                .whereEqualTo(Constants.CLASSID, className)
                .get()
                .addOnSuccessListener {
                    var checkHistory = mutableListOf<ClassCheck>()

                    for (i in it.documents) {
                        val check = i.toObject(ClassCheck::class.java)
                        if (check != null)
                        {
                            checkHistory.add(check)
                        }
                    }

                    onSuccess(checkHistory)
                }
                .addOnFailureListener {
                    Log.e("FirestoreHelper", it.message.toString())
                    onFailure()
                }
    }

    //endregion
}