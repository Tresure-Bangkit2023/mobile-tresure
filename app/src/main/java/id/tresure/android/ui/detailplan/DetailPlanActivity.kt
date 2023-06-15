package id.tresure.android.ui.detailplan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.tresure.android.databinding.ActivityDetailPlanBinding

class DetailPlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPlanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}