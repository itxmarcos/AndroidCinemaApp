package com.example.cinemaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import com.example.cinemaapp.utils.api.ApiClient
import com.example.cinemaapp.utils.data_model.Actor
import com.example.cinemaapp.utils.data_model.Genre
import com.example.cinemaapp.utils.data_model.Movie
import kotlinx.android.synthetic.main.activity_view_movie.txt_description
import kotlinx.android.synthetic.main.activity_view_movie.txt_director
import kotlinx.android.synthetic.main.activity_view_movie.txt_length
import kotlinx.android.synthetic.main.activity_view_movie.txt_rating
import kotlinx.android.synthetic.main.activity_view_movie.txt_revenue
import kotlinx.android.synthetic.main.activity_view_movie.txt_title
import kotlinx.android.synthetic.main.activity_view_movie.txt_votes
import kotlinx.android.synthetic.main.activity_view_movie.txt_year
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class EditMovie : AppCompatActivity() {
    lateinit var spinnerActors : Spinner
    lateinit var spinnerGenres : Spinner

    val movies by lazy {
        runBlocking{
            initMovies()
        }
    }

    val actors by lazy {
        runBlocking{
            initActors()
        }
    }

    val genres by lazy {
        runBlocking{
            initGenres()
        }
    }

    suspend fun initMovies() : MutableList<Movie> {
        var result = mutableListOf<Movie>()
        withContext(Dispatchers.IO) {
            result.addAll(ApiClient.movies)
        }
        return result
    }

    suspend fun initActors() : MutableList<Actor> {
        var myActors = mutableListOf<Actor>()
        withContext(Dispatchers.IO) {
            myActors.addAll(ApiClient.actors)
        }
        return myActors
    }

    suspend fun initGenres() : MutableList<Genre> {
        var myGenres = mutableListOf<Genre>()
        withContext(Dispatchers.IO) {
            myGenres.addAll(ApiClient.genres)
        }
        return myGenres
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_movie)

        txt_title.setText(intent.getStringExtra("Title"))
        txt_description.setText(intent.getStringExtra("Description"))
        txt_director.setText(intent.getStringExtra("Director"))
        txt_year.setText(intent.getStringExtra("Year"))
        txt_length.setText(intent.getStringExtra("Length"))
        txt_rating.setText(intent.getStringExtra("Rating"))
        txt_votes.setText(intent.getStringExtra("Votes"))
        txt_revenue.setText(intent.getStringExtra("Revenue"))

        /*var myActorId = ""
        spinnerActors = findViewById(R.id.spinner_actors) as Spinner
        spinnerActors.adapter = CustomAdapterActor(context = this@EditMovie, resourceId = R.layout.row_element, items = actors)
        spinnerActors.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                myActorId = actors[position].id
            }
        }*/

        /*var myGenreId = ""
        spinnerGenres = findViewById(R.id.spinner_genres) as Spinner
        adapterGenre =  CustomAdapterGenre(context = this, genres = genres.toTypedArray())
        spinnerGenres.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                myGenreId = genres[position].id
            }
        }

        var movieEdited = """{"id": """" + (movies.count()+1).toString() + """","title": """" + txt_title.text + """","description": """" + txt_description.text + """","director": """" + txt_director.text + """","year": """" + txt_year.text + """","runtime": """" + txt_length.text + """","rating": """" + txt_rating.text + """","votes": """" + txt_votes.text + """","revenue": """" + txt_revenue.text + """","genres": ["""" + myGenreId + """"],"actors": ["""" + myActorId + """"]}"""
        val urlMovie ="$REMOTE/mobile/user/getMovies.php?user=$USER&pass=$PASS"
        btn_save_changes.setOnClickListener {
            post(urlMovie, movieEdited)
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent)
        }

        //Esto se supone que es para cuando cambie el texto que sea consciente de ello
        /*txt_title.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                title = txt_title.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

        })*/
    }

    fun post(url: String, body: String): String {
        return URL(url)
            .openConnection()
            .let {
                it as HttpURLConnection
            }.apply {
                setRequestProperty("Content-Type", "application/json; charset=utf-8")
                requestMethod = "POST"
                doOutput = true
                val outputWriter = OutputStreamWriter(outputStream)
                outputWriter.write(body)
                outputWriter.flush()
            }.let {
                if (it.responseCode == 200) it.inputStream else it.errorStream
            }.let { streamToRead ->
                BufferedReader(InputStreamReader(streamToRead)).use {
                    val response = StringBuffer()
                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()
                    response.toString()
                }
            }
    }*/
    }
}
