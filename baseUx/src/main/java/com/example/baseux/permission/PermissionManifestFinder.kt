package com.example.baseux.permission

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager

class PermissionManifestFinder{
    private val TAG = "Permission"
    fun findNeededPermissionFormManifest(context: Context): List<String>{
        val packageManager = context.packageManager
        var permission = ArrayList<String>()
        try {
            val info = packageManager.getPackageInfo(context.packageName, PackageManager.GET_PERMISSIONS)
            if ((info != null).and(info.requestedPermissions.isNotEmpty()).and(info.requestedPermissionsFlags.isNotEmpty())){
                for (i in 0..info.requestedPermissions.size){
                    if (info.requestedPermissionsFlags[i] == PackageInfo.REQUESTED_PERMISSION_GRANTED) permission.add(info.requestedPermissions[i])
                }
                return permission
            }
        }catch (e: PackageManager.NameNotFoundException){
            return permission
        }
        return permission
    }
}