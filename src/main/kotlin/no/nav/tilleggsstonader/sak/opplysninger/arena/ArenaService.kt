package no.nav.tilleggsstonader.sak.opplysninger.arena

import no.nav.tilleggsstonader.kontrakter.arena.ArenaStatusDto
import no.nav.tilleggsstonader.kontrakter.arena.oppgave.ArenaOppgaveDto
import no.nav.tilleggsstonader.kontrakter.felles.IdenterRequest
import no.nav.tilleggsstonader.kontrakter.felles.IdenterStønadstype
import no.nav.tilleggsstonader.kontrakter.felles.Stønadstype
import no.nav.tilleggsstonader.sak.fagsak.domain.FagsakPersonService
import no.nav.tilleggsstonader.sak.opplysninger.pdl.PersonService
import no.nav.tilleggsstonader.sak.opplysninger.pdl.dto.identer
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ArenaService(
    private val arenaClient: ArenaClient,
    private val personService: PersonService,
    private val fagsakPersonService: FagsakPersonService,
) {

    @Cacheable("arena-status-ident")
    fun hentStatus(ident: String, stønadstype: Stønadstype): ArenaStatusDto {
        val identer = personService.hentPersonIdenter(ident).identer()
        return arenaClient.hentStatus(IdenterStønadstype(identer, stønadstype))
    }

    fun harSaker(ident: String): Boolean {
        val identer = personService.hentPersonIdenter(ident).identer()
        return arenaClient.harSaker(IdenterRequest(identer)).harSaker
    }

    @Cacheable("arena-oppgaver", cacheManager = "5secCache")
    fun hentOppgaver(fagsakPersonId: UUID): List<ArenaOppgaveDto> {
        val aktivIdent = fagsakPersonService.hentAktivIdent(fagsakPersonId)
        val identer = personService.hentPersonIdenter(aktivIdent).identer()
        return arenaClient.hentOppgaver(IdenterRequest(identer))
    }
}
