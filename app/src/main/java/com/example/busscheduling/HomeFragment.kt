package com.example.busscheduling

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.busscheduling.customdaila.CustomAleartDialog
import com.example.busscheduling.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private  val viewModel:ScheduleViewModel by activityViewModels()
    private lateinit var binding:FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        //val scheduleAdapter=ScheduleAdapter()
        /*val scheduleAdapter=ScheduleAdapter {schedule,action->
            perFormRowAction(schedule,action)

        }*/
        val scheduleAdapter=ScheduleAdapter({busSchedule ->
            viewModel.updateSchedule(busSchedule)
        },
            {busSchedule, rowAction ->
                perFormRowAction(busSchedule,rowAction)
            })
       // scheduleAdapter.submitList(scheduleList)
        //scheduleAdapter.submitList(viewModel.getAllschedule())
        viewModel.getAllschedule().observe(viewLifecycleOwner){
            scheduleAdapter.submitList(it)
        }
        binding.SceduleRV.layoutManager= LinearLayoutManager(requireActivity())
        binding.SceduleRV.adapter=scheduleAdapter
        binding.SceduleRV.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy>0){
                    binding.fab.hide()
                }
                if (dy<0){
                    binding.fab.show()
                }
            }
        })
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_newScheduleFragment)
        }
        return binding.root
    }

    private fun perFormRowAction(schedule: BusSchedule,action: RowAction) {
        //val popupMenu =PopupMenu(requireActivity())
        when(action){
            RowAction.EDIT -> {
                val bundle= bundleOf("id" to schedule.id)
                findNavController().navigate(R.id.action_homeFragment_to_newScheduleFragment,bundle)
            }
            RowAction.DELETE -> {
                CustomAleartDialog(
                    icon = R.drawable.ic_baseline_delete_24,
                    title = "Delete ${schedule.busname}?",
                    body = "Are you To delete This Item?",
                    possitiveButtomText = "YES",
                    negativeButtomText = "CANCEL",

                ){
                    viewModel.deleteSchedule(schedule)
                }.show(childFragmentManager,null)
                //viewModel.deleteSchedule(schedule)

            }
        }
    }


}