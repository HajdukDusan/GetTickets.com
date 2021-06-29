Vue.component("izmena-manifestacije", {
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
      cookie: "",
      role: "",
      manifestacija: [],
      komentari: [],
      manifestacijaNaziv: "",
      tipovi: ["FESTIVAL", "CONCERT"],
      zoom: 15,
      center: "",
      location: "",
      rotation: 0,
      cookie: "",
      role: "",
    };
  },
  mounted() {
    this.role = localStorage.getItem("role");
    this.cookie = localStorage.getItem("cookie");
    this.manifestacijaNaziv = localStorage.getItem("manifestacija");
    this.loadManifestacija();
  },
  template: `
    <div>
        <worker-nav></worker-nav>
        <link rel="stylesheet" href="css/page_style.css" type="text/css">
<b-card id="page_content">
          <b-form @submit="onSubmit">
       <b-form-group id="input-group-1" label="Naziv manifestacije:" label-for="input-1">
        <b-form-input
          id="input-1"
          v-model="manifestacija.name"
          placeholder="Unesite naziv manifestacije"
          required
        ></b-form-input>
        </b-form-group>
            <b-form-group id="input-group-1" label="Tip manifestacije:" label-for="input-1">
                <b-form-select
                :options ="tipovi"
                placeholder="Izaberite tip manifestacije"
          id="input-1"
          v-model="manifestacija.manifestationType"
          required
        ></b-form-select>
        </b-form-group>
            <b-form-group id="input-group-1" label="Cena karte:" label-for="input-1">
                <b-form-input
                type = "number"
                placeholder="Unesite cenu karte"
          id="input-1"
          v-model="manifestacija.regularPrice"
          min = "1"
          required
        ></b-form-input>
        </b-form-group>
            <b-form-group id="input-group-1" label="Broj sedista:" label-for="input-1">
                <b-form-input
                type = "number"
                placeholder="Unesite broj sedista"
          id="input-1"
          v-model="manifestacija.numberOfSeats"
          min = "1"
          required
        ></b-form-input>
        </b-form-group inline>
        <b-row>
          <b-label>Datum i vreme:</b-label>
          <b-col>
        <b-form-input
            type = "date"
            id="input-1"
            v-model="manifestacija.date"
            min = "min"
            required
        ></b-form-input>
            </b-col>
            <b-col>
        <b-form-input
            type = "time"
            id="input-1"
            v-model="manifestacija.time"
            min = "min"
            required
        ></b-form-input>
            </b-col>
        </b-row>
        <b-row>
        <b-label>Adresa: </b-label>
        <b-col>
        <b-form-input
            placeholder="Unesite adresu"
            id="input-1"
            v-model="manifestacija.location.address"
            required
        ></b-form-input>
        </b-col>
        <b-col>
        <b-form-input
            placeholder="Unesite grad"
            id="input-1"
            v-model="manifestacija.location.city"
            required
        ></b-form-input>
        </b-col>
        <b-col>
        <b-form-input
            placeholder="Unesite drzavu"
            id="input-1"
            v-model="manifestacija.location.country"
            required
        ></b-form-input>
        </b-col>
        </b-row>
                <br>
        <b-row>
        <vl-map data-projection="EPSG:4326" @click="changeLocation($event.coordinate)" style="height: 500px; width: 500px">
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
        </b-row>
          <b-button type="submit">Sacuvaj manifestaciju</b-button>
        </b-form>
</form>
  </b-card>

    </div>`,
  methods: {
    changeLocation(evt) {
      this.location = evt;
    },
    onSubmit() {
      this.manifestacija.dateTime =
        this.manifestacija.date + "T" + this.manifestacija.time;

      this.manifestacija.location.latitude = this.location[0];
      this.manifestacija.location.longitude = this.location[1];
      console.log(this.manifestacija);
      axios
        .post(`rest/manifestation/updateManifestation`, this.manifestacija)
        .then((response) => alert("Uspesno dodata manifestacija"))
        .catch((error) => {
          console.log("Greska.");
          alert("Vec postoji manifestaciju u ovo vreme na ovom mestu");
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
          console.log(this.manifestacija.dateTime);
          this.manifestacija.date = this.manifestacija.dateTime.split("T")[0];
          this.manifestacija.time =
            this.manifestacija.dateTime.split("T")[1].split(":")[0] +
            ":" +
            this.manifestacija.dateTime.split("T")[1].split(":")[1];
          console.log(this.manifestacija.time);
          this.center = this.location;
          console.log(this.location);
        });
    },
  },
});
