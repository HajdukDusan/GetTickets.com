Vue.component("prikaz-manifestacije", {
  data: function () {
    var today = new Date();
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
      preostalaKarte: 0,
      brojKarti: 0,
      options: ["REGULAR", "FAN_PIT", "VIP"],
      tipKarte: "REGULAR",
      vipCena: "",
      fanPitCena: "",
      cenaKarti: "",
      userType: "",
      popust: "",
      canComment: false,
      komentar: {
        text: "",
        grade: 0,
        user: "",
        manifestation: "",
      },
    };
  },
  mounted() {
    this.role = localStorage.getItem("role");
    if (this.role == null) {
      this.role = "default";
    }
    this.cookie = localStorage.getItem("cookie");
    if (this.cookie == null) {
      this.cookie = "";
    }
    if (this.role === "user") {
      this.loadUserType();
    }
    this.manifestacijaNaziv = localStorage.getItem("manifestacija");
    this.loadManifestacija();
    this.loadKomentare();
    this.loadProdateKarte();
  },
  template: `
  <div>
          <link rel="stylesheet" href="css/home.css" type="text/css">
          <div v-if="role  === 'default'">
          <default-nav></default-nav>
        </div>
          <div v-if="role  === 'admin'">
          <admin-nav></admin-nav>
        </div>
        <div v-if="role  === 'worker'">
          <worker-nav></worker-nav>
        </div>
        <div v-if="role  === 'user'">
          <korisnik-nav></korisnik-nav>
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
              Cena karte: {{ manifestacija.regularPrice}}
            <b-button pill v-b-popover.hover="'VIP cena:' + vipCena + ' FAN_PIT cena:' + fanPitCena"size="lg" variant="outline-dark">i</b-button> <br>
            Lokacija: {{ manifestacija.location.address}}, {{ manifestacija.location.city}}, {{ manifestacija.location.country}}<br>
            Broj sedista: {{manifestacija.numberOfSeats}} <br>
            Preostalo karti: {{preostalaKarte}} <br>
            <div v-if="manifestacija.status === 'FINISHED'">
                Status: Odrzana <br>
                Prosecna ocena: <br>
            </div>
            
                       <!-- KUPOVINA KARTI-->
            <div v-if="manifestacija.status === 'APPROVED'">
              <div v-if="preostalaKarte != 0">
                <div v-if="role === 'user'">
                <b-row>
                  <b-col>
                  <p>	Tip kupca: {{this.userType}} <b-button pill v-b-popover.hover="'BRONZE - bez popusta            SILVER - 3% popusta GOLD - 5% popust'" size="lg" variant="outline-dark">i</b-button> </p>
                  </b-col>
                </b-row>
           
              <b-row>
                  <b-col>
                  
                  <b-form-select
                  v-on:change="updateCenu"
                v-model="tipKarte"
                :options="options"
                required
              ></b-form-select> 
                              </b-col>
                  <b-col>
                    <b-form-input
                    v-on:change="updateCenu"
                    type="number"
                    min=1
                    v-model="brojKarti">
                </b-form-input>
                  </b-col>
                  <b-col>
                  <b-button v-on:click="kupiKarte">Kupite karte</b-button>
                  </b-col>
                  <b-row>
                    <p>Ukupna cena: {{cenaKarti}}</p>
                  </b-row>
                
                  </b-row>
                </div>
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
          <div v-for="komentar in komentari">
            <b-media right-align vertical-align="center">
              <template #aside>
                    <b-form-rating
                id="rating-readonly"
                v-model="komentar.grade"
                no-border
                readonly
                variant="warning"
              ></b-form-rating>
              </template>
              <h5 class="mt-0 mb-1">{{komentar.user}}</h5>
              <p class="mb-0">
                {{komentar.text}}
              </p>
            </b-media>
            <hr>
            </div>
            <div v-if="canComment">
              <b-form @submit="submitKom">
                <b-form-textarea
                required
                v-model="komentar.text"
                id="textarea-default"
                placeholder="Unesite komentar"
              ></b-form-textarea>
              <b-form-rating v-model="komentar.grade" variant="warning" no-border required></b-form-rating>
                 <b-button type="submit" style="float: right">Komentarisi</b-button>
              </b-form>
            </div>

          </div>

        </b-card>
    




  </div>
     `,
  methods: {
    submitKom() {
      axios.post(`rest/comment/addComment`, this.komentar);
    },
    checkIfCanComment() {
      if (this.manifestacija.status === "FINISHED") {
        axios
          .get(
            `rest/manifestation/checkIfAttended/cookie=${this.cookie}/manifestation=${this.manifestacijaNaziv}`
          )
          .then((response) => {
            console.log(response.data);
            this.canComment = response.data;
          });
      }
    },
    updateCenu() {
      if (this.tipKarte === "VIP") {
        this.cenaKarti = this.brojKarti * this.manifestacija.regularPrice * 4;
        this.cenaKarti = this.cenaKarti - this.cenaKarti * this.popust;
        console.log(this.cenaKarti);
      } else if (this.tipKarte == "FAN_PIT") {
        this.cenaKarti = this.brojKarti * this.manifestacija.regularPrice * 2;
        this.cenaKarti = this.cenaKarti - this.cenaKarti * this.popust;
        console.log(this.cenaKarti);
      } else {
        this.cenaKarti = this.brojKarti * this.manifestacija.regularPrice;
        this.cenaKarti = this.cenaKarti - this.cenaKarti * this.popust;
        console.log(this.cenaKarti);
      }
    },
    loadUserType() {
      axios.get(`rest/user/userInfo/${this.cookie}`).then((response) => {
        if (this.userType != "") {
          if (this.userType != response.data.userType.type) {
            alert(`Presli ste na ${response.data.userType.type} korisnika!!`);
          }
        }
        this.userType = response.data.userType.type;
        this.popust = response.data.userType.discount;
      });
    },
    kupiKarte() {
      if (this.preostalaKarte < this.brojKarti) {
        alert("Uneli ste veci broj karti od onih na stanju");
        return;
      }
      if (confirm("Da li ste sigurn da zelite da kupite karte")) {
        axios
          .get(
            `rest/card/buyCards/type=${this.tipKarte}/number=${this.brojKarti}/user=${this.cookie}/Manifestation=${this.manifestacijaNaziv}`
          )
          .then((response) => {
            alert(response.data);
            //this.loadManifestacija();
            this.loadUserType();
            this.loadProdateKarte();
          })
          .catch((error) => {
            alert("Nema dovoljno karti na lageru");
          });
      }
    },
    loadProdateKarte() {
      axios
        .get(
          `rest/card/manifestationReservedCardsCount/${this.manifestacijaNaziv}`
        )
        .then((response) => {
          this.preostalaKarte = response.data;
        });
    },
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
          this.fanPitCena = this.manifestacija.regularPrice * 2;
          this.vipCena = this.manifestacija.regularPrice * 4;
          this.center = this.location;
          this.checkIfCanComment();
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
