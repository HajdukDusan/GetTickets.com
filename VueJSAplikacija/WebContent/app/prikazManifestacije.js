Vue.component("prikaz-manifestacije", {
  data: function () {
    let map = new ol.Map({
      target: "mapOL",
      layers: [
        new ol.layer.Tile({
          source: new ol.source.OSM(),
        }),
      ],

      view: new ol.View({
        center: [0, 0],
        zoom: 2,
      }),
    });
    return {
      manifestacija: [],
      komentari: [],
      manifestacijaNaziv: "",
      zoom: 15,
      center: "",
      location: "",
      rotation: 0,
      cookie: "",
      role: "",
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.role = localStorage.getItem("role");
    this.manifestacijaNaziv = localStorage.getItem("manifestacija");
    this.loadManifestacija();
    this.loadKomentare();
  },
  template: `
  <div>
          <link rel="stylesheet" href="css/home.css" type="text/css">
          <div v-if="role  === 'admin'">
          <admin-nav></admin-nav>
        </div>
        <div v-if="role  === 'worker'">
          <worker-nav></worker-nav>
        </div>
        <div v-if="role  === 'user'">
          <korisnik-nav></korisnik-nav>
        </div>
        <div v-if="role  === ''">
          <defaul-nav></defaul-nav>
        </div>

                <b-card

        id="page_content"
        :header="manifestacija.name"
        class="text-left"
        bg-variant="light"
        :img-src="manifestacija.eventPoster"
        img-width = "1000"
        img-height = "250"
        style="font-size:25px;">
        
        <b-row>
        <b-col>
            Tip: {{ manifestacija.manifestationType }} <br>
            Datum: {{ manifestacija.dateTime }} <br>
            Cena karte: {{ manifestacija.regularPrice}} <br>
            Lokacija: {{ manifestacija.location.address}}, {{ manifestacija.location.city}}, {{ manifestacija.location.country  }}<br>
            <div v-if="manifestacija.status == false">
                Status: Nije jos odrzana <br>
            </div>
            <div v-else>
                Status: Odrzana <br>
                Prosecna ocena:... <br>
            </div>
            </b-col>
            <b-col>
            
            
            <!-- PRIKAZ NA MAPI -->
                        <vl-map data-projection="EPSG:4326" style="height: 500px; width: 500px">
				<vl-view :zoom.sync="zoom" :center.sync="center" :rotation.sync="rotation"></vl-view>
					<vl-layer-tile>
					<vl-source-osm></vl-source-osm>
					</vl-layer-tile>
                    <vl-feature>
						<vl-geom-point :coordinates="location">
						</vl-geom-point>
						<vl-style-box>
							<vl-style-icon
									src="/VueJSAplikacija/images/marker.png"
									:scale="0.05"
									:anchor="[0.5, 1]"
							></vl-style-icon>
						</vl-style-box>
					</vl-feature>
				</vl-map>
                </b-col>
                <br>
        </b-row>
        <b-row>
            <h2>
            Komentari
            </h2>
            <br>
                    <b-row>
          <div  v-for="komentar in komentari">
        
            <b-media right-align vertical-align="center">
              <template #aside>
                <b-form-rating v-model="komentar.grade"></b-form-rating>
              </template>
              <h5 class="mt-0 mb-1">{{komentar.user}}</h5>
              <p class="mb-0">
                {{komentar.text}}
              </p>
            </b-media>
            <hr>
          </div>

        </b-card>
    




  </div>
     `,
  methods: {
    loadManifestacija() {
      axios
        .get(`rest/manifestation/${this.manifestacijaNaziv}`)
        .then((response) => {
          console.log(response);
          this.manifestacija = response.data;
          this.location = [
            this.manifestacija.location.latitude,
            this.manifestacija.location.longitude,
          ];
          this.center = this.location;
          console.log(this.location);
        });
    },
    loadKomentare() {
      axios
        .get(`rest/comment/manifestation=${this.manifestacijaNaziv}`)
        .then((response) => {
          console.log(response);
          this.komentari = response.data;
        });
    },
    fixDate(manifestacija) {
      manifestacija.dateTime = manifestacija.dateTime.split("T")[0];
      return manifestacija.dateTime;
    },
  },
});
