package ph.devcon.android.sponsor.service;

import com.google.common.collect.ArrayListMultimap;

import java.util.List;

import ph.devcon.android.base.service.BaseAPICacheService;
import ph.devcon.android.sponsor.api.SponsorBaseResponse;
import ph.devcon.android.sponsor.db.Sponsor;

/**
 * Created by lope on 10/6/14.
 */
public interface SponsorService extends BaseAPICacheService<List<Sponsor>, SponsorBaseResponse> {
    public ArrayListMultimap<String, Sponsor> buildMultimap(List<Sponsor> sponsors);
}