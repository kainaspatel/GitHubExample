package com.githubexample.android.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import android.util.Base64.encodeToString
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.githubexample.android.R
import kotlinx.android.synthetic.main.layout_no_data.view.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import java.net.Inet4Address
import java.net.NetworkInterface
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

const val TAG = "AppUtils"

var progressDialog: ProgressDialog? = null

fun showProgressDialog(context: Context, view: View) {
    try {
        progressDialog = ProgressDialog(context)
        if (!progressDialog!!.isShowing) {
            progressDialog?.setMessage("Please wait...")
            progressDialog?.setCancelable(false)
            progressDialog?.show()
        }
    } catch (e: Throwable) {
        Log.e(TAG, e.message)
        e.printStackTrace()
    }

}

fun stopProgress() {
    try {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog?.dismiss()
        }
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}


fun toast(context: Context?, text: String?) {
    if (context != null && text != null) {
        val toast = android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT)
//        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 200)
        toast.show()
    }
}

fun toast(context: Context?, text: Int?) {
    if (context != null && text != null) {
        val toast = android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT)
//        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 200)
        toast.show()
    }


}


fun isValidText(str: String): Boolean {
    val expression = "^[a-zA-Z\\s]+"
    return str.matches(expression.toRegex())
}

fun isValidUserName(str: String): Boolean {
    val expression = "^[a-zA-Z0-9]*$"
    return str.matches(expression.toRegex())
}

fun isValidMail(mailString: String): Boolean {
    return !TextUtils.isEmpty(mailString) && android.util.Patterns.EMAIL_ADDRESS.matcher(mailString).matches()
}


fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}

@SuppressLint("MissingPermission", "HardwareIds")
fun getAndroidId(context: Context): String {
    val deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    println("$TAG, getAndroidId : deviceId >> $deviceId")
    return deviceId
}

fun getImageRequestBody(file: File): RequestBody {
    return RequestBody.create(MediaType.parse("image/*"), file)
}

fun getStringRequestBody(strParam: String): RequestBody {
    return RequestBody.create(MediaType.parse("text/plain"), strParam)
}

fun getIntRequestBody(intParam: Int): RequestBody {
    return RequestBody.create(MediaType.parse("text/plain"), intParam.toString())
}

fun getLocalIpAddress(): String {
    try {
        val en = NetworkInterface.getNetworkInterfaces()
        while (en.hasMoreElements()) {
            val intf = en.nextElement()
            val enumIpAddr = intf.inetAddresses
            while (enumIpAddr.hasMoreElements()) {
                val inetAddress = enumIpAddr.nextElement()
                if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                    return inetAddress.getHostAddress()
                }
            }
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    return ""
}

fun validatePassword(password: String): Boolean {
    val strPattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$"
    var pattern: Pattern = Pattern.compile(strPattern)
    var matcher: Matcher = pattern.matcher(password)
    return matcher.matches()
}

fun convertDateTime(fromFormat: String, toFormat: String, dateOriginalGot: String): String {

    try {
        //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //// Getting Source format here
        val fmt = SimpleDateFormat(fromFormat)

        fmt.timeZone = TimeZone.getDefault()

        val date = fmt.parse(dateOriginalGot)

        //SimpleDateFormat fmtOut = new SimpleDateFormat("EEE, MMM d, ''yyyy");

        //// Setting Destination format here
        val fmtOut = SimpleDateFormat(toFormat)

        return fmtOut.format(date)

    } catch (e: Exception) {

        e.printStackTrace()

        e.message

    }

    return ""

}


fun showNoInternetLayout(view: View) {
    view.llParentNoData.visibility = View.VISIBLE
    view.tvNOdata.text = view.context.getString(R.string.network_error)
//    view.ivNoData.setImageResource(R.drawable.ic_no_connection)
}

fun hideNoInternetLayout(view: View) {
    view.llParentNoData.visibility = View.GONE
}


fun showNoDataLayout(view: View, str: String = "No data!") {
    view.llParentNoData.visibility = View.VISIBLE
    view.tvNOdata.text = str
//    view.ivNoData.setImageResource(R.drawable.ic_no_data)
}

fun hideNoDataLayout(view: View) {
    view.llParentNoData.visibility = View.GONE
}


fun showSomethingWentWrongDataLayout(view: View) {
    view.llParentNoData.visibility = View.VISIBLE
    view.tvNOdata.text = view.resources.getString(R.string.something_went_wrong)
//    view.ivNoData.setImageResource(R.drawable.ic_something_wrong)
}

fun formatString(d: Double): String {
    return if (d == d.toLong().toDouble())
        String.format("%d", d.toLong())
    else
        String.format("%s", d)
}


fun hasPermissions(context: Context?, permissions: Array<String>): Boolean {
    if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
    }
    return true
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun submitSearchQuery(context: Context, query: String) {
//    toast(context, query)
//    val intent = Intent(context, ProductListActivity::class.java)
//    intent.putExtra("search_query", query)
//    context.startActivity(intent)
}


fun encodeStringToBase64(str: String): String {
    val data = str.toByteArray(charset("UTF-8"))
    return encodeToString(data, android.util.Base64.DEFAULT)
}

fun decodeBase64(base64: String): String {
    return try {
        val data = android.util.Base64.decode(base64, android.util.Base64.DEFAULT)
        String(data, charset("UTF-8"))
    } catch (e: Exception) {
        Log.e(TAG, "decodeBase64 >> $e")
        base64
    }
}

fun isColorValid(colorString: String): Boolean {
    val colorPattern = Pattern.compile("#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})")
    val m = colorPattern.matcher(colorString)
    return m.matches()
}


fun loadJSONFromAsset(context: Context, fileName: String): String? {
    val json: String?
    try {
        val inputStream = context.assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        json = String(buffer)
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }

    return json
}


fun decodeUnicode(theString: String): String {
    try {
        var aChar: Char
        val len = theString.length
        val outBuffer = StringBuffer(len)
        var x = 0
        while (x < len) {
            aChar = theString[x++]
            if (aChar == '\\') {
                aChar = theString[x++]
                if (aChar == 'u') {
                    // Read the xxxx
                    var value = 0
                    for (i in 0..3) {
                        aChar = theString[x++]
                        when (aChar) {
                            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> value = (value shl 4) + aChar.toInt() - '0'.toInt()
                            'a', 'b', 'c', 'd', 'e', 'f' -> value = (value shl 4) + 10 + aChar.toInt() - 'a'.toInt()
                            'A', 'B', 'C', 'D', 'E', 'F' -> value = (value shl 4) + 10 + aChar.toInt() - 'A'.toInt()
                            else -> throw IllegalArgumentException(
                                    "Malformed   \\uxxxx   encoding.")
                        }

                    }
                    outBuffer.append(value.toChar())
                } else {
                    if (aChar == 't')
                        aChar = '\t'
                    else if (aChar == 'r')
                        aChar = '\r'
                    else if (aChar == 'n')
                        aChar = '\n'
//                    else if (aChar == 'f')
//                        aChar = '\f'
                    outBuffer.append(aChar)
                }
            } else
                outBuffer.append(aChar)
        }
        return outBuffer.toString()
    } catch (e: Exception) {
        return theString
    }
}

fun showDialogPermissionRational(context: Context, strMessage: String) {
    val builder = AlertDialog.Builder(context)

    // Set the alert dialog title
    builder.setTitle("Permission Required")

    // Display a message on alert dialog
    builder.setMessage(strMessage)

    // Set a positive button and its click listener on alert dialog
    builder.setPositiveButton("YES") { _, _ ->
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

//    builder.setNegativeButton("No") { dialog, which ->
//    }
//
//    builder.setNeutralButton("Cancel") { _, _ ->
//    }

    val dialog: AlertDialog = builder.create()
//    dialog.setCancelable(false)
    dialog.show()
}

fun isMyServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
    val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as (ActivityManager)
    for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            return true
        }
    }
    return false
}
