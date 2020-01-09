package com.kreactive.test.app.presentation

import androidx.appcompat.app.AppCompatActivity
import com.kreactive.test.app.domain.PermissionManager

abstract class BaseActivity : AppCompatActivity() {


    protected val permissionManager: PermissionManager by lazy {
        PermissionManager.getInstance(applicationContext)
    }

    //region Permissions

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        permissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //endregion
}
