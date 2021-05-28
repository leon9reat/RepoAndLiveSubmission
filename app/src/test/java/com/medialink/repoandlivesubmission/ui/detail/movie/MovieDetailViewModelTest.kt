package com.medialink.repoandlivesubmission.ui.detail.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.medialink.repoandlivesubmission.data.source.remote.ApiConfig
import com.medialink.repoandlivesubmission.data.source.remote.entity.*
import com.medialink.repoandlivesubmission.data.source.remote.entity.movie.*
import com.medialink.repoandlivesubmission.data.source.remote.repository.MovieRepository
import com.medialink.repoandlivesubmission.data.source.remote.retrofit.ApiService
import com.medialink.repoandlivesubmission.data.source.remote.retrofit.RetrofitClient
import com.medialink.repoandlivesubmission.utils.Resource
import com.medialink.repoandlivesubmission.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private var apiService: ApiService = RetrofitClient.getApiService()

    @Mock
    private val mockMovieRepository: MovieRepository = MovieRepository(apiService)

    @Mock
    private lateinit var mockMovie: Observer<Resource<Movie>>

    @Mock
    private lateinit var mockReview: Observer<Resource<List<Review>>>

    @Mock
    private lateinit var mockVideo: Observer<Resource<List<Video>>>

    private val movie = Movie(
        false,
        "/fPGeS6jgdLovQAKunNHX8l0avCy.jpg",
        null,
        0,
        null,
        "https://animemafia.in",
        567189,
        "tt0499097",
        "en",
        "Tom Clancy's Without Remorse",
        "An elite Navy SEAL uncovers an international conspiracy while seeking justice for the murder of his pregnant wife.",
        2235.578,
        "/rEm96ib0sPiZBADNKBHKBv5bve9.jpg",
        null,
        null,
        "2021-04-29",
        0,
        109,
        null,
        "Released",
        "From Tom Clancy, the author of Rainbow Six.",
        "Tom Clancy's Without Remorse",
        false,
        7.3,
        991
    )

    private val review = listOf(
        Review(
            "Cat Ellington",
            AuthorDetails(
                "Cat Ellington",
                "CatEllington",
                "/uCmwgSbJAcHqNwSvQvTv2dB95tx.jpg",
                null
            ),
            "(As I'm writing this review, Darth Vader's theme music begins to build in my mind...)\r\n\r\nWell, it actually has a title, what the Darth Vader theme. And that title is \"The Imperial March\", composed by the great John Williams, whom, as many of you may already know, also composed the theme music for \"Jaws\" - that legendary score simply titled, \"Main Title (Theme From Jaws)\".\r\n\r\nNow, with that lil' bit of trivia aside, let us procede with the fabled film currently under review: Star Wars. It had been at a drive-in theater in some small Illinois town or other where my mother, my older brother, and I had spent our weekly \"Movie Date Night\" watching this George Lucas directed cult masterpiece from our car in the parking lot. On the huge outdoor screen, the film appeared to be a silent one, but thanks to an old wire-attached speaker, we were able to hear both the character dialogue and soundtrack loud and clear. We even had ourselves a carful of vittles and snacks - walked back to our vehicle, of course, from the wide-opened cinema's briefly distant concession stand. Indeed, it had been a lovely summer evening that July.\r\n\r\nFrom the time the film started, with my brother and I following along as our mother sped-read the opening crawl, I began to feel rather antsy, thinking that this movie, the first in a franchise that would soon be world-renowned, was going to be boring, due to its genre being Science Fiction: A respectably likable, but not a passionately lovable genre of mine DURING THAT TIME. I just didn't believe I was going to like Star Wars all that much ... But I soon found myself intrigued ... And awed.\r\n\r\nGeorge Lucas is a man with a phenomenal, and I do mean phenomenal imagination. Apart from his human characters (Han, Luke, Leia, and Obi-Wan Kenobi, among others), the droids: C-3P0, R2-D2, R2-series, and IG-88, not to mention those unusual characters like Jabba the Hutt, Yoda, and Chewbacca, just to name a few, are all creations of Lucas's phenomenal imagination. And I was completely in awe of each one of these strange beings. Then there was Vader ... And the evil Emperor ... And the Stormtroopers ... And the Spacecraft ... And the galaxies (I'll admit that I am a huge lover of the Universe in all its Celestial glory) ... And the magnificent planets ... The Lightsabers ... And so on. Star Wars is a gorgeously shot space opera; it is truly an epic masterpiece. We enjoyed this film tremendously. And my brother was a die-hard fan from that night onward. He, my brother, had even received for Christmas that year, nearly every Star Wars action figure that my mother could find, including two of the spacecraft: The Millennium Falcon and Star Destroyer. The Death Star space station had too been wrapped beneath our Christmas tree - tagged with his name. It was totally crazy, what the new Star Wars era. Frenzied! But it was great ... Even still, to this day.\r\n\r\nI don't personally know anyone whom has yet to see Star Wars, but that certainly doesn't suggest there are still a few people out there who haven't. And if you're one of the latter, then you should know that this classic space opera comes highly recommended. The entire series is told backwards, so you'll definitely want to see Star Wars first, followed by its two sequels: The Empire Strikes Back and Return of the Jedi ... In that order. I trust that you'll too discover yourself to be a lifelong cult fan in the wake. 😊",
            "2017-02-13T22:23:01.268Z",
            "58a231c5925141179e000674",
            "2017-02-13T23:16:19.538Z",
            "https://www.themoviedb.org/review/58a231c5925141179e000674"
        )
    )

    private val reviewRespon = ReviewRespon(
        567189,
        1,
        review,
        1,
        3
    )

    private val videoRespon = VideoRespon(
        567189,
        listOf(
            Video(
                "533ec654c3a36854480003eb",
                "en",
                "US",
                "SUXWAEX2jlg",
                "Trailer 1",
                "YouTube",
                720,
                "Trailer"
            )
        )
    )

    @Before
    fun setUp() {
    }

    @Test
    fun test_loadMovie() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(movie)
                .`when`(mockMovieRepository)
                .getMovie(movie.id ?: 0, ApiConfig.LANGUAGE)

            val viewModel = MovieDetailViewModel(mockMovieRepository)
            viewModel.fetchMovie(movie.id ?: 0)
            viewModel.getMovie().observeForever(mockMovie)
            Mockito.verify(mockMovieRepository).getMovie(movie.id ?: 0, ApiConfig.LANGUAGE)
            Mockito.verify(mockMovie).onChanged(Resource.success(movie))
            viewModel.getMovie().removeObserver(mockMovie)
        }
    }

    @Test
    fun test_loadMovieError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(mockMovieRepository)
                .getMovie(movie.id ?: 0, ApiConfig.LANGUAGE)
            val viewModel = MovieDetailViewModel(mockMovieRepository)

            viewModel.fetchMovie(movie.id ?: 0)
            viewModel.getMovie().observeForever(mockMovie)
            Mockito.verify(mockMovieRepository).getMovie(movie.id ?: 0, ApiConfig.LANGUAGE)
            Mockito.verify(mockMovie).onChanged(
                Resource.error(
                    null,
                    errorMessage,
                )
            )
            viewModel.getMovie().removeObserver(mockMovie)
        }
    }

    @Test
    fun test_loadReview() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(reviewRespon)
                .`when`(mockMovieRepository)
                .getMovieReview(movie.id ?: 0, ApiConfig.LANGUAGE, 1)

            val viewModel = MovieDetailViewModel(mockMovieRepository)
            viewModel.fetchReview(movie.id ?: 0)
            viewModel.getReview().observeForever(mockReview)
            Mockito.verify(mockMovieRepository).getMovieReview(movie.id ?: 0, ApiConfig.LANGUAGE, 1)
            Mockito.verify(mockReview)
                .onChanged(Resource.success(reviewRespon.results) as Resource<List<Review>>?)
            viewModel.getReview().removeObserver(mockReview)
        }
    }

    @Test
    fun test_loadReviewError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(mockMovieRepository)
                .getMovieReview(movie.id ?: 0, ApiConfig.LANGUAGE, 1)
            val viewModel = MovieDetailViewModel(mockMovieRepository)

            viewModel.fetchReview(movie.id ?: 0)
            viewModel.getReview().observeForever(mockReview)
            Mockito.verify(mockMovieRepository).getMovieReview(movie.id ?: 0, ApiConfig.LANGUAGE, 1)
            Mockito.verify(mockReview).onChanged(
                Resource.error(
                    null,
                    errorMessage,
                )
            )
            viewModel.getReview().removeObserver(mockReview)
        }
    }

    @Test
    fun test_loadVideo() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(videoRespon)
                .`when`(mockMovieRepository)
                .getMovieVideo(movie.id ?: 0, ApiConfig.LANGUAGE)

            val viewModel = MovieDetailViewModel(mockMovieRepository)
            viewModel.fetchVideo(movie.id ?: 0)
            viewModel.getVideo().observeForever(mockVideo)
            Mockito.verify(mockMovieRepository).getMovieVideo(movie.id ?: 0, ApiConfig.LANGUAGE)
            Mockito.verify(mockVideo)
                .onChanged(Resource.success(videoRespon.results) as Resource<List<Video>>?)
            viewModel.getVideo().removeObserver(mockVideo)
        }
    }

    @Test
    fun test_loadVideoError() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Error Message For You"
            Mockito.doThrow(RuntimeException(errorMessage))
                .`when`(mockMovieRepository)
                .getMovieVideo(movie.id ?: 0, ApiConfig.LANGUAGE)
            val viewModel = MovieDetailViewModel(mockMovieRepository)

            viewModel.fetchVideo(movie.id ?: 0)
            viewModel.getVideo().observeForever(mockVideo)
            Mockito.verify(mockMovieRepository).getMovieVideo(movie.id ?: 0, ApiConfig.LANGUAGE)
            Mockito.verify(mockVideo).onChanged(
                Resource.error(
                    null,
                    errorMessage,
                )
            )
            viewModel.getVideo().removeObserver(mockVideo)
        }
    }
}