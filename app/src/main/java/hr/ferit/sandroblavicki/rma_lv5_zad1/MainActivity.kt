package hr.ferit.sandroblavicki.rma_lv5_zad1

import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mSoundPool: SoundPool
    private var mLoaded: Boolean = false
    var mSoundMap: HashMap<Int, Int> = HashMap()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setUpUi()
        this.loadSounds()
    }
    private fun setUpUi() {
        findViewById<Button>(R.id.shootGun).setOnClickListener(this)
        findViewById<Button>(R.id.shootSniper).setOnClickListener(this)
    }
    private fun loadSounds() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.mSoundPool = SoundPool.Builder().setMaxStreams(10).build()
        } else {
            this.mSoundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
        }
        this.mSoundPool.setOnLoadCompleteListener { _, _, _ -> mLoaded = true }
        this.mSoundMap[R.raw.gun] = this.mSoundPool.load(this, R.raw.gun, 1)
        this.mSoundMap[R.raw.sniper] = this.mSoundPool.load(this, R.raw.sniper, 1)
    }
    override fun onClick(v: View) {
        if (this.mLoaded == false) return
        when (v.getId()) {
            R.id.shootGun -> playSound(R.raw.gun)
            R.id.shootSniper -> playSound(R.raw.sniper)
        }
    }
    fun playSound(selectedSound: Int) {
        val soundID = this.mSoundMap[selectedSound] ?: 0
        this.mSoundPool.play(soundID, 1f, 1f, 1, 0, 1f)
    }
}