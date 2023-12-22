package edu.wsc2022.a01.mrtranslate.UI

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.wsc2022.a01.mrtranslate.DataBase.LibraryHelper
import edu.wsc2022.a01.mrtranslate.ViewModel.TranslateViewModel
import edu.wsc2022.a01.mrtranslate.databinding.FragmentTranslateBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.net.HttpURLConnection
import java.net.URL


class TranslateFragment : Fragment() {
    private lateinit var viewModel: TranslateViewModel
    private lateinit var binding: FragmentTranslateBinding
    private lateinit var libData: LibData
    private lateinit var helper: LibraryHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTranslateBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        helper = LibraryHelper(requireContext())
        viewModel = ViewModelProvider(requireActivity())[TranslateViewModel::class.java]
        val toLangEdit = binding.tolgEdit
        val fromTextEdit = binding.fromtextEdit
        val toText = binding.resText
        val anim = arrowAnimation()
        binding.trbt.setOnClickListener {
            if (!fromTextEdit.text.isNullOrEmpty() && !toLangEdit.text.isNullOrEmpty()){
                val fromData = FromTextData(toLangEdit.text.toString(),fromTextEdit.text.toString())
                lifecycleScope.launch {
                    binding.arrow.startAnimation(anim)
                    binding.charsavebt.isEnabled = false
                    binding.textsavebt.isEnabled = false
                   val response = translate(fromData)
                    val resData = response.translations[0]
                    binding.arrow.clearAnimation()
                     libData = LibData(fromData.toLg,resData.detected_source_language,fromData.text,resData.text)
                    toText.text = libData.toText
                    binding.charsavebt.isEnabled = true
                    binding.textsavebt.isEnabled = true
                }
            }
        }
        binding.charsavebt.setOnClickListener {
            lifecycleScope.launch {
                viewModel.addData(libData,helper,1)
                Toast.makeText(requireContext(), "Save complete", Toast.LENGTH_SHORT).show()
                binding.charsavebt.isEnabled = false
                binding.textsavebt.isEnabled = false
            }
        }
        binding.textsavebt.setOnClickListener {
            lifecycleScope.launch {
                viewModel.addData(libData,helper,2)
                Toast.makeText(requireContext(), "Save complete", Toast.LENGTH_SHORT).show()
                binding.charsavebt.isEnabled = false
                binding.textsavebt.isEnabled = false
            }
        }

}
    private fun arrowAnimation(): Animation{
        val anim = TranslateAnimation(0f,0f,0f,-40f).apply {
            duration = 1500
            interpolator = CycleInterpolator(2f)
            repeatCount = Animation.INFINITE
        }
        return anim
    }
    private suspend fun translate(data: FromTextData): ResponseData {
        Log.d("translate", "do translate")
        var responseData = ResponseData(listOf())
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val postJson = "{\"text\": [\"${data.text}\"],\"target_lang\": \"${data.toLg}\"}"
            val contentType = "application/json".toMediaType()
            val url = "https://api-free.deepl.com/v2/translate"
            val request = Request.Builder().apply {
                url(url)
                post(postJson.toRequestBody(contentType))
                header("Authorization", "DeepL-Auth-Key $Token")
            }.build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val res = response.body?.string()
                val gson = Gson()
                val type = object : TypeToken<ResponseData>(){}.type
                responseData = gson.fromJson(res,type)
            }
        }
        return responseData
    }
//    private suspend fun post(){
//        val postJson = ("{\"text\": [\"Hello, world!\"],\"target_lang\": \"JA\"}").toByteArray()
//        withContext(Dispatchers.IO){
//            val url = URL("https://api-free.deepl.com/v2/translate")
//            val con = url.openConnection() as HttpURLConnection
//            con.apply {
//                requestMethod = "GET"
//                doOutput = true
//                setRequestProperty("Authorization","DeepL-Auth-Key $Token")
//                setRequestProperty("Content-Type","application/json")
//                outputStream.use {
//                    it.write(postJson)
//                    it.flush()
//                }
//            }
//            if (con.responseCode == 200){
//                val res = con.inputStream.bufferedReader().use { it.readText() }
//            }
//        }
//    }
}
data class FromTextData(val toLg: String,val text: String)
data class Translation(val detected_source_language: String, val text: String)
data class ResponseData(val translations: List<Translation>)
data class LibData(val fromLg: String,val toLg: String,val fromText: String,val toText: String)
data class LibraryData(val id: Int,val fromLg: String,val toLg: String,val fromText: String,val toText: String)