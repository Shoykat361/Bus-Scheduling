package com.example.busscheduling

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.busscheduling.customdaila.DatepickerFragment
import com.example.busscheduling.customdaila.TimepickerFragment
import com.example.busscheduling.databinding.FragmentNewScheduleBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class NewScheduleFragment : Fragment() {
    private  val viewModel:ScheduleViewModel by activityViewModels()
    private lateinit var binding: FragmentNewScheduleBinding
    private var from="Dhaka"
    private var to="Dhaka"
    private var bustype= bustypeEconomy
    private var id:Long?=null
    val REQUEST_IMAGE_CAPTURE =1
    private var currentPhotoPath: String?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNewScheduleBinding.inflate(inflater,container,false)
        initSpinner()
        initBusTypeRadioGroup()
        id =arguments?.getLong("id")
        if (id!=null){
            binding.savebtn.setText("Update")

            viewModel.getSchedulebyId(id!!).observe(viewLifecycleOwner){
                setData(it)
            }
        }
        binding.depaturedateid.setOnClickListener {
            DatepickerFragment {
                binding.depaturedateshow.text=it
            }.show(childFragmentManager,null)

        }
        binding.timeid.setOnClickListener {
            TimepickerFragment{
                binding.timeshow.text=it
            }.show(childFragmentManager,null)

        }
        binding.capturebtn.setOnClickListener {
            dispatchTakePictureIntent()

        }
        binding.savebtn.setOnClickListener {
            saveInfo()
        }

        return binding.root
    }



    private fun setData(it: BusSchedule) {
        binding.busnameinput.setText(it.busname)
        binding.depaturedateshow.setText(it.Depaturedate)
        binding.timeshow.setText(it.DepatureTime)
        val fromIndex= citylist.indexOf(it.from)
        val toIndex= citylist.indexOf(it.to)
        binding.fromspinershow.setSelection(fromIndex)
        binding.tospinershow.setSelection(toIndex)
        if (it.bustype== bustypeEconomy){
            binding.radiugropid.check(R.id.Economy)
        }else if (it.bustype== bustypeBusiness){
            binding.radiugropid.check(R.id.standard)
        }else if (it.bustype== bustypePremimum){
            binding.radiugropid.check(R.id.premimum)
        }

    }


    private fun saveInfo() {
        val name=binding.busnameinput.text.toString()
        val date=binding.depaturedateshow.text.toString()
        val time=binding.timeshow.text.toString()
        // required valided
        if (from==to){
            Toast.makeText(requireActivity(),
                "from and to are not same",
                Toast.LENGTH_SHORT)
                .show()
            return

        }
        val schedule=BusSchedule(
            id=System.currentTimeMillis(),
            busname = name,
            bustype = bustype,
            Depaturedate = date,
            DepatureTime = time,
            from = from,
            to = to,
            image = currentPhotoPath


        )
        if (id!=null){
            schedule.id=id!!
            viewModel.updateSchedule(schedule)
        }else{
            viewModel.addSchedule(schedule)

        }

        //scheduleList.add(schedule)
        //viewModel.addSchedule(schedule)

        //findNavController()
    // .navigate(R.id.action_newScheduleFragment_to_homeFragment)
        /*BusScheduleDB.getdb(requireActivity())
            .getScheduleDao().addSchedule(schedule)*/
        //viewModel.addSchedule(schedule)
        findNavController().popBackStack()

    }
    private fun initBusTypeRadioGroup() {
        binding.radiugropid.setOnCheckedChangeListener { radioGroup, i ->
            val rb: RadioButton = radioGroup.findViewById(i)
            bustype=rb.text.toString()
        }
    }
    private fun initSpinner() {
        val adapter= ArrayAdapter<String>(
            requireActivity(),
            android.R.layout.simple_spinner_dropdown_item, citylist
        )
        binding.fromspinershow.adapter=adapter
        binding.tospinershow.adapter=adapter
        binding.fromspinershow.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                from=p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        binding.tospinershow.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                to=p0?.getItemAtPosition(p2).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }
    private fun dispatchTakePictureIntent() {

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireActivity(),
                        "com.example.busscheduling.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.busImageIV.setImageURI(Uri.parse(currentPhotoPath))
        }
    }
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }


}