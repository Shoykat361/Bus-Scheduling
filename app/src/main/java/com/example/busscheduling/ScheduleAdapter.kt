package com.example.busscheduling

import android.app.Notification
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.busscheduling.databinding.ScheduleRowBinding

class ScheduleAdapter(val favouriteCallback:(BusSchedule)->Unit,val menuClickCallback : (BusSchedule,RowAction)-> Unit )

    : ListAdapter<BusSchedule, ScheduleAdapter.ScheduleViewholder>(ScheduleDifutil()) {
    class ScheduleViewholder(val binding:ScheduleRowBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(busSchedule: BusSchedule){
            binding.schedule=busSchedule

        }
    }

    class ScheduleDifutil : DiffUtil.ItemCallback<BusSchedule>(){
        override fun areItemsTheSame(oldItem: BusSchedule, newItem: BusSchedule): Boolean {
            return oldItem.id==newItem.id

        }

        override fun areContentsTheSame(oldItem: BusSchedule, newItem: BusSchedule): Boolean {
            return oldItem==newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewholder {
        val binding=ScheduleRowBinding.
        inflate(LayoutInflater.from(parent.context),parent,false)
        return ScheduleViewholder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewholder, position: Int) {
        //val schedule:BusSchedule=getItem(position)
        val schedule=getItem(position)
        holder.bind(schedule)
        val btn =holder.binding.menuBtn
        holder.binding.menuBtn.setOnClickListener {
            //menuClickCallback()
            val popupMenu= PopupMenu(btn.context,btn)
            popupMenu.menuInflater.inflate(R.menu.row_meu,popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                val action:RowAction=when(it.itemId){
                    R.id.item_edit ->RowAction.EDIT
                    R.id.item_delete ->RowAction.DELETE
                    else -> RowAction.NONE
                }
                menuClickCallback(schedule,action)
                true
            }
            holder.binding.favouriteIv.setOnClickListener {
                schedule.favourite = !schedule.favourite
                favouriteCallback(schedule)

            }

        }
    }

}
enum class RowAction {
    EDIT,DELETE,NONE
}
