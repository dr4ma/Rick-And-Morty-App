package com.example.rickandmortycharacters.domain.usecase

import com.example.rickandmortycharacters.domain.models.Character
import com.example.rickandmortycharacters.domain.models.Location
import com.example.rickandmortycharacters.domain.models.Origin
import com.example.rickandmortycharacters.domain.models.ResultsItem
import com.example.rickandmortycharacters.domain.repository.Repository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetAllCharactersUseCaseTest {

    @Test
    suspend fun shouldReturnDataAsInRepository() {
        val repository = mock<Repository>()

        val episodes = mutableListOf<String>()
        val results = mutableListOf<ResultsItem>()
        val location = Location(name = "nameLocation", url = "urlLocation")
        val origin = Origin(name = "nameOrigin", url = "urlOrigin")
        episodes.add("nameOne")
        episodes.add("nameTwo")
        val firstTestCharacter = ResultsItem(
            name = "name",
            created = "created",
            episode = episodes,
            gender = "gender",
            id = 0,
            image = "image",
            location = location,
            url = "url",
            origin = origin,
            species = "species",
            status = "status",
            type = "type"
        )
        results.add(firstTestCharacter)

        Mockito.`when`(repository.getAllCharacters().body()?.results!!).thenReturn(results)

        val useCase = GetAllCharactersUseCase(repository = repository)
        val actual = useCase.getAllCharacters()
        val expected = results

        Assertions.assertEquals(expected, actual)
    }
}