package com.kreactive.test.app.domain

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kreactive.test.R
import com.kreactive.test.app.presentation.BaseFragment

class PermissionManager(val context: Context) {

    companion object : SingletonHolder<PermissionManager, Context>(::PermissionManager)

    var autoIncrementRequestCode = 0
        get() {
            field = field + 1
            return field
        }

    private val requestPermissionsDataMap = mutableMapOf<Int, RequestPermissionData>()

    fun executeFunctionWithPermissionNeeded(
        activity: Activity,
        permission: String,
        onGranted: () -> Unit,
        requestCode: Int = autoIncrementRequestCode,
        refusedText: String = context.getString(R.string.permission_default_explanation),
        alwaysDenyText: String = context.getString(R.string.permission_default_always_deny)
    ) {
        executeFunctionWithPermissionNeeded(
            activity,
            arrayOf(permission),
            onGranted,
            requestCode,
            refusedText,
            alwaysDenyText
        )

    }

    fun executeFunctionWithPermissionNeeded(
        fragment: BaseFragment,
        permission: String,
        onGranted: () -> Unit,
        requestCode: Int = autoIncrementRequestCode,
        refusedText: String = context.getString(R.string.permission_default_explanation),
        alwaysDenyText: String = context.getString(R.string.permission_default_always_deny)
    ) {
        executeFunctionWithPermissionNeeded(
            fragment,
            arrayOf(permission),
            onGranted,
            requestCode,
            refusedText,
            alwaysDenyText
        )

    }


    fun executeFunctionWithPermissionNeeded(
        activity: Activity,
        permissions: Array<String>,
        onGranted: () -> Unit,
        requestCode: Int = autoIncrementRequestCode,
        refusedText: String = context.getString(R.string.permission_default_explanation),
        alwaysDenyText: String = context.getString(R.string.permission_default_always_deny)
    ) {
        executeFunctionWithPermissionNeeded(
            activity,
            permissions,
            onGranted,
            requestCode,
            {
                showRefusedPermissionDialog(
                    activity,
                    refusedText,
                    {
                        executeFunctionWithPermissionNeeded(
                            activity,
                            permissions,
                            onGranted,
                            requestCode,
                            refusedText,
                            alwaysDenyText
                        )
                    }
                )
            },
            {
                showAlwaysDenyPermissionDialog(
                    activity,
                    alwaysDenyText
                )
            }
        )
    }

    fun executeFunctionWithPermissionNeeded(
        fragment: BaseFragment,
        permissions: Array<String>,
        onGranted: () -> Unit,
        requestCode: Int = autoIncrementRequestCode,
        refusedText: String = context.getString(R.string.permission_default_explanation),
        alwaysDenyText: String = context.getString(R.string.permission_default_always_deny)
    ) {
        executeFunctionWithPermissionNeeded(
            fragment,
            permissions,
            onGranted,
            requestCode,
            {
                showRefusedPermissionDialog(
                    fragment,
                    refusedText,
                    {
                        executeFunctionWithPermissionNeeded(
                            fragment,
                            permissions,
                            onGranted,
                            requestCode,
                            refusedText,
                            alwaysDenyText
                        )
                    }
                )
            },
            {
                showAlwaysDenyPermissionDialog(
                    fragment,
                    alwaysDenyText
                )
            }
        )
    }

    fun executeFunctionWithPermissionNeeded(
        activity: Activity,
        permissions: Array<String>,
        onGranted: () -> Unit,
        requestCode: Int,
        onRefused: () -> Unit,
        onAlwaysDeny: () -> Unit
    ) {
        if (!isGrantedPermissions(permissions)) {
            requestPermission(
                activity,
                permissions,
                onGranted,
                requestCode,
                onRefused,
                onAlwaysDeny
            )
        } else {
            onGranted()
        }
    }

    fun executeFunctionWithPermissionNeeded(
        fragment: BaseFragment,
        permissions: Array<String>,
        onGranted: () -> Unit,
        requestCode: Int,
        onRefused: () -> Unit,
        onAlwaysDeny: () -> Unit
    ) {
        if (!isGrantedPermissions(permissions)) {
            requestPermission(
                fragment,
                permissions,
                onGranted,
                requestCode,
                onRefused,
                onAlwaysDeny
            )
        } else {
            onGranted()
        }
    }

    fun executeFunctionWithPermissionNeeded(
        activity: Activity,
        permission: String,
        onGranted: () -> Unit,
        requestCode: Int,
        onRefused: () -> Unit,
        onAlwaysDeny: () -> Unit
    ) {
        val permissions = arrayOf(permission)
        if (!isGrantedPermissions(permissions)) {
            requestPermission(
                activity,
                permissions,
                onGranted,
                requestCode,
                onRefused,
                onAlwaysDeny
            )
        } else {
            onGranted()
        }
    }

    fun executeFunctionWithPermissionNeeded(
        fragment: BaseFragment,
        permission: String,
        onGranted: () -> Unit,
        requestCode: Int,
        onRefused: () -> Unit,
        onAlwaysDeny: () -> Unit
    ) {
        val permissions = arrayOf(permission)
        if (!isGrantedPermissions(permissions)) {
            requestPermission(
                fragment,
                permissions,
                onGranted,
                requestCode,
                onRefused,
                onAlwaysDeny
            )
        } else {
            onGranted()
        }
    }

    private fun requestPermission(
        fragment: BaseFragment,
        permissions: Array<String>,
        onGranted: () -> Unit,
        requestCode: Int,
        onRefused: (() -> Unit)? = null,
        onAlwaysDeny: (() -> Unit)? = null
    ) {
        requestPermissionsDataMap.put(
            requestCode,
            RequestPermissionData(
                onGranted,
                onRefused,
                onAlwaysDeny
            )
        )
        fragment.requestPermissions(permissions, requestCode)
    }

    private fun requestPermission(
        activity: Activity,
        permissions: Array<String>,
        onGranted: () -> Unit,
        requestCode: Int,
        onRefused: (() -> Unit)? = null,
        onAlwaysDeny: (() -> Unit)? = null
    ) {
        requestPermissionsDataMap.put(
            requestCode,
            RequestPermissionData(
                onGranted,
                onRefused,
                onAlwaysDeny
            )
        )
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    fun onRequestPermissionsResult(
        activity: Activity,
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val permissionsData = requestPermissionsDataMap.get(requestCode)
        requestPermissionsDataMap.remove(requestCode)
        if (permissionsData != null) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                permissionsData.onGranted.invoke()
            } else {
                val permission = permissions[0]
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    permissionsData.onRefused?.invoke()
                } else {
                    permissionsData.onAlwaysDeny?.invoke()
                }
            }
        }
    }

    fun onRequestPermissionsResult(
        fragment: BaseFragment,
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        val permissionsData = requestPermissionsDataMap.get(requestCode)
        requestPermissionsDataMap.remove(requestCode)
        if (permissionsData != null) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                permissionsData.onGranted.invoke()
            } else {
                val permission = permissions[0]
                if (fragment.shouldShowRequestPermissionRationale(permission)) {
                    permissionsData.onRefused?.invoke()
                } else {
                    permissionsData.onAlwaysDeny?.invoke()
                }
            }
        }
    }

    private fun isGrantedPermissions(permissions: Array<String>): Boolean {
        var isGranted = true
        for (permission in permissions) {
            isGranted = isGranted && ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
        return isGranted
    }

    private fun showRefusedPermissionDialog(
        activity: Activity,
        explanation: String,
        onAcceptClick: () -> Unit
    ) {
        AlertDialog.Builder(activity)
            .setMessage(explanation)
            .setPositiveButton(android.R.string.yes,
                { dialog, which ->
                    onAcceptClick()
                }
            )
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun showRefusedPermissionDialog(
        fragment: BaseFragment,
        explanation: String,
        onAcceptClick: () -> Unit
    ) {
        AlertDialog.Builder(fragment.context!!)
            .setMessage(explanation)
            .setPositiveButton(android.R.string.yes,
                { dialog, which ->
                    onAcceptClick()
                }
            )
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun showAlwaysDenyPermissionDialog(activity: Activity, explanation: String) {

        AlertDialog.Builder(activity)
            .setMessage(explanation)
            .setPositiveButton(android.R.string.yes,
                { dialog, which ->
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS

                    val uri = Uri.fromParts("package", context.getPackageName(), null)
                    intent.setData(uri)

                    activity.startActivity(intent)
                }
            )
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }

    private fun showAlwaysDenyPermissionDialog(fragment: BaseFragment, explanation: String) {

        AlertDialog.Builder(fragment.context!!)
            .setMessage(explanation)
            .setPositiveButton(android.R.string.yes,
                { dialog, which ->
                    val intent = Intent()
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS

                    val uri = Uri.fromParts("package", context.getPackageName(), null)
                    intent.setData(uri)

                    fragment.startActivity(intent)
                }
            )
            .setNegativeButton(android.R.string.cancel, null)
            .show()
    }


    private data class RequestPermissionData(
        val onGranted: () -> Unit,
        val onRefused: (() -> Unit)? = null,
        val onAlwaysDeny: (() -> Unit)? = null
    )
}
