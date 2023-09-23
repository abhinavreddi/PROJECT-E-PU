package com.example.e_pu

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment

import com.example.e_pu.R


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val courses = arrayOf("PIET")
        val semesters = arrayOf("SEM3")
        val subjects = arrayOf("DM", "OOP", "DSA", "COMA", "DBMS")

        val coursesAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, courses)
        val semestersAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, semesters)
        val subjectsAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, subjects)

        val spinnerCourses = view.findViewById<Spinner>(R.id.spinner)
        val spinnerSemesters = view.findViewById<Spinner>(R.id.spinner2)
        val spinnerSubjects = view.findViewById<Spinner>(R.id.spinner3)

        spinnerCourses.adapter = coursesAdapter
        spinnerSemesters.adapter = semestersAdapter
        spinnerSubjects.adapter = subjectsAdapter

        val searchButton = view.findViewById<Button>(R.id.button)

        searchButton.setOnClickListener {
            val selectedCourse = spinnerCourses.selectedItem.toString()
            val selectedSemester = spinnerSemesters.selectedItem.toString()
            val selectedSubject = spinnerSubjects.selectedItem.toString()

            // Combine selected options to form the PDF file name
            val pdfFileName = "$selectedCourse-$selectedSemester-$selectedSubject.pdf"

            // Start PdfViewerActivity and pass the PDF file name
            val intent = Intent(requireContext(), com.example.e_pu.PdfViewerActivity::class.java)

            intent.putExtra("pdfFileName", pdfFileName)
            startActivity(intent)
        }
    }
}

