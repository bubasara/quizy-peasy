package bubasara.quizypeasy.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import bubasara.quizypeasy.R
import bubasara.quizypeasy.models.Category

class ChooseCategoriesAdapter (var context: Context, private val interfaceListener : ChooseCategoriesAdapterInterface)
    : RecyclerView.Adapter<ChooseCategoriesAdapter.ViewHolder> () {

    //  using interface to notify of any change
    interface ChooseCategoriesAdapterInterface {
        fun longClickOnCategory(position: Int)
        fun clickOnIsChecked(category: Category)
    }

    private lateinit var listOfCategories : List<Category>
    internal fun setListOfCategories(listOfCategories: List<Category>){
        this.listOfCategories = listOfCategories
    }

    //  uncheck categories (list of all categories)
    fun uncheckAllCategories()
    {
        for(category in listOfCategories) {
            category.isChecked = false
        }
    }

    //  elements in recyclerview (item category)
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val clCategoryContainerOuter : ConstraintLayout
        private val clCategoryContainerInner : ConstraintLayout
        private val txtViewCategoryName : TextView
        private val txtViewNumberOfQuestions : TextView
        private val imgViewCheck : ImageView
        private val imgViewCategoryImage : ImageView

        init {
            clCategoryContainerOuter = view.findViewById(R.id.clCategoryContainerOuter)
            clCategoryContainerInner = view.findViewById(R.id.clCategoryContainerInner)
            txtViewCategoryName = view.findViewById(R.id.txtViewCategoryName)
            txtViewNumberOfQuestions = view.findViewById(R.id.txtViewNumberOfQuestions)
            imgViewCheck = view.findViewById(R.id.imgViewCheck)
            imgViewCategoryImage = view.findViewById(R.id.imgViewCategoryImage)
        }

        //  getters
        fun getClCategoryContainerOuter() : ConstraintLayout {
            return clCategoryContainerOuter
        }

        fun getTxtViewCategoryName() : TextView {
            return txtViewCategoryName
        }

        fun getTxtViewNumberOfQuestions() : TextView {
            return txtViewNumberOfQuestions
        }

        fun getImgViewCheck() : ImageView {
            return imgViewCheck
        }

        fun getImgViewCategoryImage() : ImageView {
            return imgViewCategoryImage
        }

    }

    //  inflate category layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent,false)
        return ChooseCategoriesAdapter.ViewHolder(view)
    }

    //  binding elements with data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = listOfCategories[position]

        holder.getTxtViewCategoryName().text = category.categoryName
        holder.getTxtViewNumberOfQuestions().text = "${category.numberOfQuestions} questions"
        holder.getImgViewCheck().tag = R.drawable.square

        val imgCategory = holder.getImgViewCategoryImage().context.resources.getIdentifier(
            "category_${category.imgCategory}", "drawable", holder.getImgViewCategoryImage().context.packageName
        )

        holder.getImgViewCategoryImage().setImageDrawable(ContextCompat.getDrawable(
            holder.getImgViewCategoryImage().context, imgCategory
        ))

        //  check/uncheck category (view)
        if (category.isChecked) {
            holder.getImgViewCheck().setBackgroundResource(R.drawable.checked)
        } else {
            holder.getImgViewCheck().setBackgroundResource(R.drawable.square)
        }

        holder.getImgViewCheck().setOnClickListener {

            if (category.isChecked) {
                it.setBackgroundResource(R.drawable.square)
                category.isChecked = false
            } else {
                it.setBackgroundResource(R.drawable.checked)
                category.isChecked = true
            }

            interfaceListener.clickOnIsChecked(category)
        }

        //  catch long click on category
        holder.getClCategoryContainerOuter().setOnLongClickListener {
            interfaceListener.longClickOnCategory(position)
            true
        }
    }

    fun deleteItem(position: Int){
        notifyItemRemoved(position)
    }

    //  num of items for recyclerview
    override fun getItemCount(): Int {
        return listOfCategories.size
    }

}