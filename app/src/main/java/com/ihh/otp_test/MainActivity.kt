import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import com.ihh.otp_test.OtpApiService
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ihh.otp_test.OtpResponse
import com.ihh.otp_test.R
import com.ihh.otp_test.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val mHandler = Handler(Looper.getMainLooper())
    private val generateOtpButton : Button = findViewById(R.id.generate_otp_button)
    private val otpKeyEditText : EditText = findViewById(R.id.otp_key_edit_text)
    private val otpCodeTextView : TextView = findViewById(R.id.otp_code_text_view)

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutInflater = LayoutInflater.from(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        generateOtpButton.setOnClickListener {
            val otpKey = otpKeyEditText.text.toString()
            generateOtp(otpKey)
        }


    }

    private fun generateOtp(otpKey: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        val apiService = retrofit.create(OtpApiService::class.java)
        val call = apiService.generateOtp(otpKey)

        call.enqueue(object : Callback<OtpResponse> {
            override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
                if (response.isSuccessful) {
                    val otpCode = response.body()?.otp_code

                    mHandler.post {
                        otpCodeTextView.text = otpCode
                    }
                } else {
                    // Handle error case
                }
            }

            override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                // Handle failure case
            }
        })
    }
}
