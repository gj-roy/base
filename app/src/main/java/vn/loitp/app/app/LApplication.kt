package vn.loitp.app.app

import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.common.Constants
import com.core.utilities.LSharedPrefsUtil
import com.core.utilities.LUIUtil
import com.data.ActivityData
import com.data.AdmobData
import com.google.firebase.messaging.FirebaseMessaging
import io.realm.Realm
import io.realm.RealmConfiguration
import vn.loitp.app.R
import vn.loitp.app.activity.api.truyentranhtuan.db.TTTDatabase
import vn.loitp.app.activity.database.room.db.FNBDatabase

//build release de check
//TODO crash FloatingViewActivity -> demo app -> floating view crash android 9
//TODO demo -> youtube parser ko vao list video dc
//TODO demo -> floating view crash
//TODO demo firebase -> auth
//TODO service -> ko stop service dc
//TODO database -> read sqlite dtb crash

//need add nice repo
//https://github.com/hackware1993/MagicIndicator
//https://github.com/stfalcon-studio/StfalconImageViewer
//https://github.com/stfalcon-studio/StfalconImageViewer/issues/6

//GIT
//combine 2 commit gan nhat lam 1, co thay doi tren github
/*git reset --soft HEAD~2
git push -f*/

@LogTag("LApplication")
class LApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

        //config admob id
        AdmobData.instance.idAdmobFull = getString(R.string.str_f)

        //config activity transition default
        ActivityData.instance.type = Constants.TYPE_ACTIVITY_TRANSITION_SLIDELEFT

        //config realm
        val realmConfiguration = RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)

        //config font
        LUIUtil.fontForAll = Constants.FONT_PATH

        //fcm
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.FCM_TOPIC)

        //room database
        FNBDatabase.getInstance(this)

        //ttt database
        TTTDatabase.getInstance(this)

//        logD("LApplication onCreate")
        LUIUtil.setDarkTheme(isDarkTheme = true)

        //init key
        LSharedPrefsUtil.instance.putString(LSharedPrefsUtil.KEY_CORE_GALLERY_FLICKR, getString(R.string.loitp_core_gallery_flickr))
        LSharedPrefsUtil.instance.putString(LSharedPrefsUtil.KEY_CORE_COMIC_MUP, getString(R.string.loitp_core_comic_mup))
    }
}
