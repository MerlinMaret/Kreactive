package com.kreactive.test.app.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kreactive.test.app.domain.PermissionManager

abstract class BaseFragment : Fragment() {

    protected val permissionManager: PermissionManager by lazy {
        val context = context?.applicationContext ?: throw UnknownError()
        PermissionManager.getInstance(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
    }

    /**
     * This methode is call on view created.
     * Init your views here
     */
    abstract fun initView(view: View, savedInstanceState: Bundle?)

    //region Permissions

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        activity?.let {
            permissionManager.onRequestPermissionsResult(
                it,
                requestCode,
                permissions,
                grantResults
            )
        }
    }

    //endregion
}
