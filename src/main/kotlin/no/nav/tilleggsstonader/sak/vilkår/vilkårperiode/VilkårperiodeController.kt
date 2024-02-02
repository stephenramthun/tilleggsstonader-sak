package no.nav.tilleggsstonader.sak.vilkår.vilkårperiode

import no.nav.security.token.support.core.api.ProtectedWithClaims
import no.nav.tilleggsstonader.sak.tilgang.AuditLoggerEvent
import no.nav.tilleggsstonader.sak.tilgang.TilgangService
import no.nav.tilleggsstonader.sak.vilkår.vilkårperiode.dto.LagreVilkårperiode
import no.nav.tilleggsstonader.sak.vilkår.vilkårperiode.dto.LagreVilkårperiodeResponse
import no.nav.tilleggsstonader.sak.vilkår.vilkårperiode.dto.SlettVikårperiode
import no.nav.tilleggsstonader.sak.vilkår.vilkårperiode.dto.Vilkårperioder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(path = ["/api/vilkarperiode"])
@ProtectedWithClaims(issuer = "azuread")
@Validated
class VilkårperiodeController(
    private val tilgangService: TilgangService,
    private val vilkårperiodeService: VilkårperiodeService,
) {

    @GetMapping("behandling/{behandlingId}")
    fun hentMålgrupper(@PathVariable behandlingId: UUID): Vilkårperioder {
        tilgangService.validerTilgangTilBehandling(behandlingId, AuditLoggerEvent.ACCESS)

        return vilkårperiodeService.hentVilkårperioder(behandlingId)
    }

    @PostMapping
    fun opprettVilkårMedPeriode(
        @RequestBody vilkårperiode: LagreVilkårperiode,
    ): LagreVilkårperiodeResponse {
        tilgangService.validerTilgangTilBehandling(vilkårperiode.behandlingId, AuditLoggerEvent.UPDATE)
        tilgangService.validerHarSaksbehandlerrolle()

        return vilkårperiodeService.opprettVilkårperiodeOgValiderStønadsperioder(vilkårperiode)
    }

    @PostMapping("{id}")
    fun oppdaterPeriode(
        @PathVariable("id") id: UUID,
        @RequestBody vilkårperiode: LagreVilkårperiode,
    ): LagreVilkårperiodeResponse {
        tilgangService.validerTilgangTilBehandling(vilkårperiode.behandlingId, AuditLoggerEvent.UPDATE)
        tilgangService.validerHarSaksbehandlerrolle()

        return vilkårperiodeService.oppdaterVilkårperiodeOgValiderStønadsperioder(id, vilkårperiode)
    }

    @DeleteMapping("{id}")
    fun slettPeriode(
        @PathVariable("id") id: UUID,
        @RequestBody slettVikårperiode: SlettVikårperiode,
    ): LagreVilkårperiodeResponse {
        tilgangService.validerTilgangTilBehandling(slettVikårperiode.behandlingId, AuditLoggerEvent.UPDATE)
        tilgangService.validerHarSaksbehandlerrolle()

        return vilkårperiodeService.slettVilkårperiodeOgValiderStønadsperioder(id, slettVikårperiode)
    }
}
