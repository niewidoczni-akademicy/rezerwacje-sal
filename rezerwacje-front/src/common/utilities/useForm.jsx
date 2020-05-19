//custom react hook for forms
import { useState, useEffect } from "react";

const useForm = (initState, callback, validate) => {
  const [values, setValues] = useState(initState);
  const [errors, setErrors] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = event => {
    console.log(event)
    const { name, value } = event.target;
    setValues({
      ...values,
      [name]: value
    });
  };

  const setState = state => {
    setValues(state)
  }

  const handleSubmit = async event => {
    event.preventDefault();
    setErrors(await validate(values));
    setIsSubmitting(true);
  };

  useEffect(() => {
    if (Object.keys(errors).length === 0 && isSubmitting) {
      callback();
    }
  }, [errors]);

  return {
    handleChange,
    handleSubmit,
    values,
    errors,
    setState
  };
};

export default useForm;