package com.example.baseux.permission

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity

private var permissionCode = 0

data class Permission(val granted: (Boolean) -> Unit)

object PermissionManager {
    val permissions = HashMap<Int, Permission>()

    fun onRequestPermissionResult(activity: AppCompatActivity, requestCode: Int, permission: Array<out String>, grantResults: IntArray) {
        if (permissions.containsKey(requestCode)) {
            val granted = permissions.remove(requestCode) ?: return
            val isTip = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission[0])
            val isDenied = grantResults.isNotEmpty().and(grantResults[0] != PackageManager.PERMISSION_GRANTED)
            granted.granted.invoke(isDenied.not())
//            if (isDenied) {
//                if (isTip) requestPermission(activity, permission, granted.granted)
//                else granted.granted.invoke(false)
//            } else granted.granted.invoke(true)
        }
    }
}

private fun requestPermission(activity: AppCompatActivity, permissions: Array<out String>, granted: (Boolean) -> Unit){
    val requestCode = ++permissionCode
    PermissionManager.permissions[requestCode] = Permission(granted)
    ActivityCompat.requestPermissions(activity, permissions, requestCode)
}

private fun requestPermission(fragment: androidx.fragment.app.Fragment, permissions: Array<out String>, granted: (Boolean) -> Unit){
    val requestCode = ++permissionCode
    PermissionManager.permissions[requestCode] = Permission(granted)
    fragment.requestPermissions(permissions, requestCode)
}

fun AppCompatActivity.askPermission(granted: (Boolean) -> Unit){
    val permissions = PermissionManifestFinder().findNeededPermissionFormManifest(this)
    requestPermission(this, permissions.toTypedArray(), granted)
}

fun AppCompatActivity.askLocationPermission(granted: (Boolean) -> Unit){
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.LOCATION) == PackageManager.PERMISSION_GRANTED) granted(true)
    else requestPermission(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), granted)
}

fun AppCompatActivity.askStoragePermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.STORAGE) == PackageManager.PERMISSION_GRANTED) granted(true)
    else requestPermission(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), granted)
}

fun AppCompatActivity.askContactsPermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.CONTACTS) == PackageManager.PERMISSION_GRANTED) granted(true)
    else requestPermission(this, arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_CONTACTS), granted)
}

fun AppCompatActivity.askPhonePermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.PHONE) == PackageManager.PERMISSION_GRANTED) granted(true)
    else requestPermission(this, arrayOf(
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.WRITE_CALL_LOG,
        Manifest.permission.USE_SIP,
        Manifest.permission.PROCESS_OUTGOING_CALLS,
        Manifest.permission.ADD_VOICEMAIL), granted)
}

fun AppCompatActivity.askCalendarPermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.CALENDAR) == PackageManager.PERMISSION_GRANTED) granted(true)
    else requestPermission(this, arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR), granted)
}

fun AppCompatActivity.askCameraPermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.CAMERA) == PackageManager.PERMISSION_GRANTED) granted(true)
    else requestPermission(this, arrayOf(Manifest.permission.CAMERA), granted)
}

fun AppCompatActivity.askSensorsPermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.SENSORS) == PackageManager.PERMISSION_GRANTED) granted(true)
    else requestPermission(this, arrayOf(Manifest.permission.BODY_SENSORS), granted)
}

fun AppCompatActivity.askMicrophonePermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.MICROPHONE) == PackageManager.PERMISSION_GRANTED) granted(true)
    else requestPermission(this, arrayOf(Manifest.permission.RECORD_AUDIO), granted)
}

fun AppCompatActivity.askSmsPermission(granted: (Boolean) -> Unit) {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission_group.SMS) == PackageManager.PERMISSION_GRANTED) granted(true)
    else requestPermission(this, arrayOf(
        Manifest.permission.READ_SMS,
        Manifest.permission.RECEIVE_WAP_PUSH,
        Manifest.permission.RECEIVE_MMS,
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.SEND_SMS), granted)
}