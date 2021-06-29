Vue.component("temp", {
  data: function () {
    return {
      cookie: "",
      role: "",
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.role = localStorage.getItem("role");
  },
  template: `
    <div>
    
    
    
    
    </div>

    `,
  methods: {},
});
