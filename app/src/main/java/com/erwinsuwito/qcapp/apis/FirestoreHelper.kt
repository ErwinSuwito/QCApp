package com.erwinsuwito.qcapp.apis

import com.erwinsuwito.qcapp.model.*
import com.google.firebase.firestore.*
import kotlinx.coroutines.flow.merge

class FirestoreHelper {

    private val mFireStore = FirebaseFirestore.getInstance()

    //region Classes

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

    fun getClassList(onSuccess: (MutableList<Classroom>) -> Unit, onFailure: () -> Unit)
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

    fun getClassDetail(classId: String, onSuccess: (Classroom?) -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection("classes")
                .document(classId)
                .get()
                .addOnSuccessListener {
                    onSuccess(it.toObject(Classroom::class.java))
                }
                .addOnFailureListener {
                    onFailure()
                }
    }

    //endregion

    //region Issues

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

    fun getIssue(issueId: String, onSuccess: (Issue?) -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection("issues")
                .document(issueId)
                .get()
                .addOnSuccessListener {
                    onSuccess(it.toObject(Issue::class.java))
                }
                .addOnFailureListener {
                    onFailure()
                }
    }

    fun getIssueList(onSuccess: (MutableList<Issue>) -> Unit, onFailure: () -> Unit )
    {
        mFireStore.collection("issues")
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
                .addOnFailureListener { onFailure() }
    }

    fun getIssueList(className: String, onSuccess: (MutableList<Issue>) -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection("issues")
                .whereEqualTo("className", className)
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
                .addOnFailureListener { onFailure() }
    }

    //endregion

    //region Tasks

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

    fun getTask(taskId: String, onSuccess: (Task?) -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection("tasks")
                .document(taskId)
                .get()
                .addOnSuccessListener {
                    onSuccess(it.toObject(Task::class.java))
                }
                .addOnFailureListener {
                    onFailure()
                }
    }

    fun getTasksList(onSuccess: (MutableList<Task>) -> Unit, onFailure: () -> Unit )
    {
        mFireStore.collection("issues")
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
                .addOnFailureListener { onFailure() }
    }

    fun getLongRunningIssues(onSuccess: (MutableList<Task>) -> Unit, onFailure: () -> Unit )
    {
        mFireStore.collection("issues")
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
                .addOnFailureListener { onFailure() }
    }

    //endregion

    //region Troubleshooting steps

    fun addIssueSteps(issue: Issue, step: Steps, onSuccess: () -> Unit, onFailure: () -> Unit)
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

    fun addTaskSteps(task: Task, step: Steps, onSuccess: () -> Unit, onFailure: () -> Unit)
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

    fun getIssueSteps(issueId: String, isIssue: Boolean, onSuccess: (MutableList<Steps>) -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection("issues")
                .whereEqualTo("issueId", issueId)
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
                }
    }

    fun getTaskSteps(issueId: String, isIssue: Boolean, onSuccess: (MutableList<Steps>) -> Unit, onFailure: () -> Unit)
    {
        mFireStore.collection("tasks")
                .whereEqualTo("issueId", issueId)
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
                }
    }

    //endregion

    //region Checks

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

    fun getCheckHistory(className: String, onSuccess: (MutableList<ClassCheck>) -> Unit, onFailure: () -> Unit) {
        mFireStore.collection("checks")
                .whereEqualTo("className", className)
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
                    onFailure()
                }
    }

    //endregion
}