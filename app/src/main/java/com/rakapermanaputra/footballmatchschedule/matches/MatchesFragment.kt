package com.rakapermanaputra.footballmatchschedule.matches


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.view.*
import com.rakapermanaputra.footballmatchschedule.R
import com.rakapermanaputra.footballmatchschedule.adapter.ViewPagerAdapter
import com.rakapermanaputra.footballmatchschedule.matches.lastmatches.LastMatchFragment
import com.rakapermanaputra.footballmatchschedule.matches.nextmatches.NextMatchFragment
import com.rakapermanaputra.footballmatchschedule.matches.searchmatches.SearchMatchActivity
import org.jetbrains.anko.startActivity


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MatchesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        val adapter = ViewPagerAdapter(childFragmentManager)
        val lastMatchFragment = LastMatchFragment()
        val nextMatchFragment = NextMatchFragment()
        val vPager = view?.findViewById<ViewPager>(R.id.viewPager)
        val tabs = view?.findViewById<TabLayout>(R.id.tabs)
        adapter.populateFragment(lastMatchFragment, "Last Match")
        adapter.populateFragment(nextMatchFragment, "Next Match")
        vPager?.adapter = adapter
        tabs?.setupWithViewPager(vPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_search, menu)

        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView?

        searchView?.queryHint = "Search matches"

        searchView?.setOnQueryTextListener(object : android.support.v7.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                context?.startActivity<SearchMatchActivity>("query" to query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                return false
            }
        })
    }


}
