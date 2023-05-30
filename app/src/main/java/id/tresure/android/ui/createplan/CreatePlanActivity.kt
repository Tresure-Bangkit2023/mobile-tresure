package id.tresure.android.ui.createplan

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import id.tresure.android.databinding.ActivityCreatePlanBinding
import id.tresure.android.ui.detailplan.DetailPlanActivity

class CreatePlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePlanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            btnSavePlan.setOnClickListener {
                val intent = Intent(this@CreatePlanActivity, DetailPlanActivity::class.java)
                startActivity(
                    intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this@CreatePlanActivity)
                        .toBundle()
                )
            }
        }
    }

}