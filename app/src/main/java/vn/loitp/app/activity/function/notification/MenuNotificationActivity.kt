package vn.loitp.app.activity.function.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.core.app.NotificationCompat
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LNotification
import com.function.notification.Notti
import com.function.notification.NottiFactory
import com.function.notification.actions.ContentAction
import com.function.notification.actions.NotificationAction
import com.function.notification.config.LightSettings
import com.function.notification.config.NottiConf
import com.function.notification.config.VibrationSettings
import kotlinx.android.synthetic.main.activity_notification_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.SplashActivity

@LogTag("MenuNotificationActivity")
@IsFullScreen(false)
class MenuNotificationActivity : BaseFontActivity(), View.OnClickListener {
    companion object {
        const val KEY_NOTI_DATA_INTENT = "KEY_NOTI_DATA_INTENT"
    }

    private var notti: Notti? = null
    private val channelId = "my_package_channel"
    private var notificationManager: NotificationManager? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_notification_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val notiData = intent.getStringExtra(KEY_NOTI_DATA_INTENT)
        notiData?.let { d ->
            tvMenu.text = d
        }

        notti = Notti(this, NottiConf(R.mipmap.ic_launcher, VibrationSettings(*VibrationSettings.STD_VIBRATION), LightSettings(Color.BLUE)))

        btSimpleNotification.setOnClickListener(this)
        btSimpleNotificationActions.setOnClickListener(this)
        btBigTextNotification.setOnClickListener(this)
        btInboxNotification.setOnClickListener(this)
        btBigPictureNotification.setOnClickListener(this)
        btNotificationHeadsup.setOnClickListener(this)
        btNotificationHeadsupNice.setOnClickListener(this)

        goToNotificationSettings(this)
    }

    private fun goToNotificationSettings(context: Context) {
        val packageName = context.packageName
        try {
            var intent = Intent()
            when {
                Build.VERSION.SDK_INT > Build.VERSION_CODES.O -> {
                    //intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    //intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    intent = Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS")
                    intent.putExtra("android.provider.extra.CHANNEL_ID", channelId)
                    intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName())
                }
                Build.VERSION.SDK_INT == Build.VERSION_CODES.O -> {
                    intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    intent.putExtra("android.provider.extra.APP_PACKAGE", packageName)
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
                    intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                    intent.putExtra("app_package", packageName)
                    intent.putExtra("app_uid", context.applicationInfo.uid)
                }
                Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT -> {
                    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    intent.addCategory(Intent.CATEGORY_DEFAULT)
                    intent.data = Uri.parse("package:$packageName")
                }
                else -> return
            }

            startActivity(intent)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(v: View) {
        val intent: Intent?
        when (v) {
            btSimpleNotification -> {
                notti?.show(NottiFactory
                        .get(NottiFactory.TYPE.STANDARD, "some text", "some content")
                        .setContentAction(ContentAction(Intent(this, MenuNotificationActivity::class.java), this)))
            }
            btSimpleNotificationActions -> {
                intent = Intent(this, MenuNotificationActivity::class.java)

                val actionsList = mutableListOf(NotificationAction("action", intent, this),
                        NotificationAction("action 2", intent, this))

                notti?.show(NottiFactory
                        .get(NottiFactory.TYPE.STANDARD, "some text", "some content")
                        .setActions(actionsList))
            }
            btBigTextNotification -> {
                notti?.show(NottiFactory
                        .get(NottiFactory.TYPE.BIG_TEXT, "some text", "some content").setBigText(
                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam posuere arcu enim, ut imperdiet sem pellentesque quis.Morbi in tempus lorem. Integer venenatis risus sit amet dolor lobortis, et consequat neque luctus. Etiam ut est nulla. Quisque turpis sapien, aliquet a consequat in, lacinia ut neque. Praesent scelerisque maximus nisi, sed pharetra nulla varius id. Proin at augue purus. Aliquam ut ullamcorper lorem, at vehicula nisl. Pellentesque imperdiet nunc vitae quam consectetur tempus. Nullam vel auctor orci. Ut a turpis ac quam placerat vestibulum. Sed ac hendrerit lorem, non imperdiet neque. Sed nisl urna, eleifend ac sem et, accumsan consectetur felis. Quisque cursus interdum erat, sit amet maximus felis consectetur ac. Aenean luctus, mi nec elementum bibendum, felis felis lacinia justo, vitae lacinia ligula nibh ut nulla. Nunc viverra commodo augue, in cursus nulla."))
            }
            btInboxNotification -> {
                notti?.show(NottiFactory
                        .get(NottiFactory.TYPE.INBOX, "some text", "some content")
                        .addInboxItem("some item").addInboxItem("another item")
                        .addInboxItem("and final item").setInboxSummary("random summary"))
            }
            btBigPictureNotification -> {
                val icon = BitmapFactory.decodeResource(this.resources, R.mipmap.ic_launcher)
                val iconBig = BitmapFactory.decodeResource(this.resources, R.drawable.iv)
                notti?.show(NottiFactory
                        .get(NottiFactory.TYPE.BIG_PICTURE, "some text", "some " + "content")
                        .setBigPicture(iconBig).setLargeIcon(icon))
            }
            btNotificationHeadsup -> {
                createNotification("Testttttttttttttttttttttttttt")
            }
            btNotificationHeadsupNice -> {
                val title = "This is title " + System.currentTimeMillis()
                val body = "This is body " + System.currentTimeMillis()
                val iconRes = R.mipmap.ic_launcher
                val pendingIntent = Intent(this, MenuNotificationActivity::class.java)
                pendingIntent.putExtra(KEY_NOTI_DATA_INTENT, "KEY_NOTI_DATA_INTENT " + System.currentTimeMillis())
                //pendingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                pendingIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                LNotification.showNotification(title = title, body = body, iconRes = iconRes, intent = pendingIntent)
            }
        }
    }

    private fun createNotification(aMessage: String) {
        val NOTIFY_ID = 1002

        // There are hardcoding only for show it's just strings
        val name = channelId
        val id = "my_package_channel_1" // The user-visible name of the channel.
        val description = "my_package_first_channel" // The user-visible description of the channel.

        val intent: Intent
        val pendingIntent: PendingIntent
        val builder: NotificationCompat.Builder

        if (notificationManager == null) {
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            var mChannel: NotificationChannel? = notificationManager?.getNotificationChannel(id)
            if (mChannel == null) {
                mChannel = NotificationChannel(id, name, importance)
                mChannel.description = description
                mChannel.enableVibration(true)
                mChannel.lightColor = Color.GREEN
                mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
                notificationManager?.createNotificationChannel(mChannel)
            }
            builder = NotificationCompat.Builder(this, id)

            intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            builder.setContentTitle(aMessage)  // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(this.getString(R.string.app_name))  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400))
        } else {
            builder = NotificationCompat.Builder(this)
            intent = Intent(this, SplashActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            builder.setContentTitle(aMessage)                           // required
                    .setSmallIcon(android.R.drawable.ic_popup_reminder) // required
                    .setContentText(this.getString(R.string.app_name))  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setTicker(aMessage)
                    .setVibrate(longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)).priority = Notification.PRIORITY_HIGH
        } // else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

        val notification = builder.build()
        notificationManager?.notify(NOTIFY_ID, notification)
    }
}
