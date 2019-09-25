package com.example.androidassignment.dashboard.view

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidassignment.R
import com.example.androidassignment.helper.adapter.RecyclerCallback
import com.example.androidassignment.helper.adapter.UniversalAdapter
import com.example.androidassignment.base.BaseActivity
import com.example.androidassignment.databinding.ActivityMainBinding
import com.example.androidassignment.databinding.RowUserinfolistBinding
import com.example.androidassignment.dashboard.pojo.Row
import com.example.androidassignment.dashboard.pojo.UserInfomodel
import com.example.androidassignment.dashboard.viewmodel.UserInfoviewmodel
import com.example.androidassignment.helper.extention.hideView
import com.example.androidassignment.helper.extention.showView
import com.example.androidassignment.storage.database.DatabaseClient
import com.example.androidassignment.storage.entity.UserInfoEntity
import com.jakewharton.fliptables.FlipTableConverters
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlin.collections.ArrayList
import kotlin.concurrent.thread


class MainActivity : BaseActivity() {


    private lateinit var databaseClient: DatabaseClient
    private lateinit var mAdapter: UniversalAdapter<UserInfoEntity, RowUserinfolistBinding>
    private lateinit var binding: ActivityMainBinding
    private var userinfo_list: UserInfomodel? = null
    lateinit var mViewModel: UserInfoviewmodel

    companion object {


    }

    /**
     * @param TAG: activity tag to filter out any log statement
     */
    override fun TAG() = "MainActivity"

    /**
     * put your root layout here
     */
    override fun getLayout() = R.layout.activity_main


    /**
     * Initialise :
     *
     * @param viewDataBinding : view binding (root layout)
     */

    override fun initViews(viewDataBinding: ViewDataBinding, savedInstanceState: Bundle?) {
        binding = viewDataBinding as ActivityMainBinding
        binding.errorView.visibility= View.GONE
        binding.myswipeRefreshlayout.setOnRefreshListener { getUserInfo(true) }
        binding.retry.setOnClickListener{ getUserInfo(true) }
        databaseClient =  DatabaseClient.getInstance(this@MainActivity)
        initToolbar()
        initViewModel()

    }

    /**
     * Toolbar initialization
     */

    private fun initToolbar(){
        txt_title.text = ""
        img_back.setOnClickListener{onBackPressed()}
    }

    /**
     * Viewmodel initialization
     */

    private fun initViewModel() {




        mViewModel = ViewModelProviders.of(this).get(UserInfoviewmodel::class.java)
        binding.shimmerViewContainer.showView()
        binding.shimmerViewContainer.startShimmerAnimation()

        mViewModel.loadingVisibility.observe(this, Observer { anyprogress ->

            // any progressloader you want to show/hide

            error_view.visibility= View.GONE

            if(anyprogress){
                binding.myswipeRefreshlayout.isRefreshing = if(shimmer_view_container.visibility == View.GONE) true else false
            }else{
                binding.myswipeRefreshlayout.isRefreshing = false
                binding.shimmerViewContainer.hideView()
                binding.shimmerViewContainer.stopShimmerAnimation()
            }
        })
        getUserInfo(false)

    }


    /**
     * @param flag : If API call is forcefully applied (true/false)
     *
     * */

    fun getUserInfo(flag: Boolean) {

        // check database first

        thread {
            loge(TAG(),"database list is--"+databaseClient.appDatabase.userInfoDao().all)

            if( databaseClient.appDatabase.userInfoDao().all.size <= 0){

                runOnUiThread {

                    mViewModel.getUserInfo(flag).observe(this, Observer { liveDataWrapper ->

                        if (isSuccess(liveDataWrapper).first) {
                            //userinfo_list = ArrayList()
                            userinfo_list = UserInfomodel(rows = (liveDataWrapper.data as UserInfomodel).rows,title = (liveDataWrapper.data as UserInfomodel).title)

                            thread {
                                databaseClient.appDatabase.userInfoDao().drop()
                                for(list in userinfo_list!!.rows){
                                    databaseClient.appDatabase.userInfoDao().insert(UserInfoEntity(title = list.title ,serial_number =null,description =list.description,imageHref = list.imageHref,toolbar_title = userinfo_list!!.title))
                                }
                                loge(TAG(),"database--"+FlipTableConverters.fromIterable(databaseClient.appDatabase.userInfoDao().all,UserInfoEntity::class.java))
                            }

                            onUIRefresh()
                        }
                    })

                }

            }else{


                userinfo_list = UserInfomodel(rows = databaseClient.appDatabase.userInfoDao().all,title = databaseClient.appDatabase.userInfoDao().all[0].toolbar_title!!)


                runOnUiThread {


                    onUIRefresh()

                }
            }

        }


    }

    /**
     * error from API call
     * @param error_msg : error message
     */

    override fun isInternetConnect(error_msg : String) {
        super.isInternetConnect(error_msg)

            // show error : as you like

        binding.shimmerViewContainer.hideView()
        binding.shimmerViewContainer.stopShimmerAnimation()

        if(:: mAdapter.isInitialized){
            showError(error_msg)
        }else{
            binding.errorTxt.text=error_msg
            binding.errorView.visibility= View.VISIBLE
        }


    }


    /**
     * refresh any UI contain from here
     */

    private fun onUIRefresh() {

        // Refresh screen layout and parms.

        userinfo_list?.let {

            txt_title.text = userinfo_list!!.title
            val list : ArrayList<UserInfoEntity> = ArrayList(userinfo_list!!.rows.size) // change list to array list
            list.addAll(userinfo_list!!.rows)

            recyclerview.apply {

                if (::mAdapter.isInitialized) {

                    mAdapter.notifyDataSetChanged()

                } else {
                    mAdapter =
                        UniversalAdapter(this@MainActivity,
                            list, R.layout.row_userinfolist, object :
                                RecyclerCallback<RowUserinfolistBinding, UserInfoEntity> {
                                override fun bindData(binder: RowUserinfolistBinding, model: UserInfoEntity) {
                                    binder.row = model
                                    binder.executePendingBindings()
                                }
                            })
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = mAdapter
                }


            }


        }


    }


    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}
